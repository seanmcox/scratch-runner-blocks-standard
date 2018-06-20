package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.RenderableChild;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.WhenClicked;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadScriptTuple;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class WhenClickedTest {

	@Test
	void testOpcode() {
		WhenClicked op = new WhenClicked();
		assertEquals("whenClicked",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		WhenClicked op = new WhenClicked();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		WhenClicked op = new WhenClicked();

		ClickReportingRuntime runtime = new ClickReportingRuntime();
		assertEquals(0,runtime.listeners.size());
		op.applicationStarted(runtime);
		assertEquals(1,runtime.listeners.size());
	}

	@SuppressWarnings("serial")
	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			WhenClicked op = new WhenClicked();
			ClickReportingRuntime runtime = new ClickReportingRuntime();
			op.applicationStarted(runtime);
			MouseListener mouseListener = runtime.listeners.iterator().next();
			LocationSprite sprite0 = new LocationSprite(0, 0);
			LocationSprite sprite1 = new LocationSprite(20, 20);
			ScriptTuple scriptTuple0 = new ContextualizedScriptTuple(sprite0);
			ScriptTuple scriptTuple1 = new ContextualizedScriptTuple(sprite1);
			
			assertNull(runtime.scriptRun);
			
			// Run unregistered
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertEquals(null, runtime.scriptRun);
			
			runtime.renderableChildren = new RenderableChild[] {sprite0,sprite1};
			op.registerListeningScript(scriptTuple0, new Object[] {});
			op.registerListeningScript(scriptTuple1, new Object[] {});
			// Run registered
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertEquals(scriptTuple0, runtime.scriptRun);
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 20, 20, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertEquals(scriptTuple1, runtime.scriptRun);

			// Try clicking someplace unimportant.
			runtime.scriptRun = null;
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, -20, 20, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertEquals(null, runtime.scriptRun);

			
			runtime.scriptRun = null;
			op.unregisterListeningScript(scriptTuple0, new Object[] {});
			op.unregisterListeningScript(scriptTuple1, new Object[] {});
			// Run unregistered
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertNull(runtime.scriptRun);
			mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 20, 20, 1, false));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertNull(runtime.scriptRun);
		}
	}

	@SuppressWarnings("serial")
	@Test
	void testConcurrency() {
		WhenClicked op = new WhenClicked();
		ClickReportingRuntime runtime = new ClickReportingRuntime();
		op.applicationStarted(runtime);
		MouseListener mouseListener = runtime.listeners.iterator().next();
		LocationSprite sprite = new LocationSprite(0, 0);
		runtime.renderableChildren = new RenderableChild[] {sprite};
		ScriptTuple scriptTuple0 = new ContextualizedScriptTuple(sprite);

		assertEquals(0,runtime.runners.size());

		op.registerListeningScript(scriptTuple0, new Object[] {});
		// Run registered
		mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException t) {
			fail("Interrupted");
		}
		assertEquals(1,runtime.runners.size()); // Runner was created.
		ScriptTupleRunner oldRunner = runtime.runners.values().iterator().next();

		mouseListener.mouseClicked(new MouseEvent(new Component() {}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 0, 0, 1, false));
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException t) {
			fail("Interrupted");
		}
		assertEquals(1,runtime.runners.size()); // Runner was created.
		assertNotEquals(oldRunner, runtime.runners.values().iterator().next());
	}
	
	public static class ClickReportingRuntime extends AllBadRuntime{
		public HashSet<MouseListener> listeners = new HashSet<>();
		public HashMap<ScriptTuple,ScriptTupleRunner> runners = new HashMap<>();
		public ScriptTuple scriptRun = null;
		public RenderableChild[] renderableChildren;

		public ClickReportingRuntime() {
		}

		@Override
		public RenderableChild[] getAllRenderableChildren() {
			return renderableChildren;
		}

		@Override
		public void addStageMouseListener(MouseListener listener) {
			listeners.add(listener);
		}


		@Override
		public void removeStageMouseListener(MouseListener listener) {
			listeners.remove(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#startScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, boolean)
		 */
		@Override
		public ScriptTupleRunner startScript(ScriptTuple script) {
			this.scriptRun = script;
			ScriptTupleRunner retval;
			retval = new AllBadRunner();
			runners.put(script,retval);
			return retval;
		}
	}
	
	public static class ContextualizedScriptTuple extends AllBadScriptTuple {
		private ScriptContext context;

		public ContextualizedScriptTuple(ScriptContext context) {
			this.context = context;
		}

		@Override
		public ScriptContext getContext() {
			return context;
		}
	}

	public static class LocationSprite extends AllBadSprite {
		private Object lock = new Object();
		private double x;
		private double y;
		
		/**
		 * @param x
		 * @param y
		 */
		public LocationSprite(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public void setScratchY(double scratchY) {
			y = scratchY;
		}
		
		@Override
		public void setScratchX(double scratchX) {
			x = scratchX;
		}
		
		@Override
		public void gotoXY(double scratchX, double scratchY) {
			x = scratchX;
			y = scratchY;
		}
		
		@Override
		public Area getSpriteShape() {
			Ellipse2D.Double shape = new Ellipse2D.Double(-1, -1, 2, 2);
			return new Area(shape);
		}
		
		@Override
		public Object getSpriteLock() {
			return lock;
		}
		
		@Override
		public double getScratchY() {
			return y;
		}
		
		@Override
		public double getScratchX() {
			return x;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}
	}
}
