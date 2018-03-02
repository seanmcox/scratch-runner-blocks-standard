package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.SpriteListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.Ypos;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;
import com.shtick.utils.scratch.runner.standard.blocks.util.BasicAbstractValueListener;

class YposTest {

	@Test
	void testOpcode() {
		Ypos op = new Ypos();
		assertEquals("ypos",op.getOpcode());
	}

	@Test
	void testArgs() {
		YposSetSprite sprite = new YposSetSprite();
		Ypos op = new Ypos();

		{
			sprite.setScratchY(10);
			Object value = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertEquals(new Double(10),value);
		}

		{
			sprite.setScratchY(-10);
			Object value = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertEquals(new Double(-10),value);
		}
		
		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item"});
		}
		
		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null});
		}
	}

	@Test
	void testListenerAdding() {
		YposSetSprite sprite = new YposSetSprite();
		YposSetSprite sprite2 = new YposSetSprite();
		Ypos op = new Ypos();
		final Object[] change1 = new Object[] {null,null};
		final Object[] change2 = new Object[] {null,null};
		BasicAbstractValueListener listener = new BasicAbstractValueListener(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {}) {
			@Override
			public void valueUpdated(Object oldValue, Object newValue) {
				change1[0] = oldValue;
				change1[1] = newValue;
			}
		};
		BasicAbstractValueListener listener2 = new BasicAbstractValueListener(new AllBadRuntime(), new AllBadRunner(), sprite2, new Object[] {}) {
			@Override
			public void valueUpdated(Object oldValue, Object newValue) {
				change2[0] = oldValue;
				change2[1] = newValue;
			}
		};
		
		assertEquals(0,sprite.getListeners().size());
		assertEquals(0,sprite2.getListeners().size());
		
		op.addValueListener(listener);
		
		assertEquals(1,sprite.getListeners().size());
		assertEquals(0,sprite2.getListeners().size());
		
		op.addValueListener(listener2);
		
		assertEquals(1,sprite.getListeners().size());
		assertEquals(1,sprite2.getListeners().size());
		
		{
			SpriteListener l = sprite.getListeners().iterator().next();
			Point2D oldP = new Point2D.Double(1,1);
			Point2D newP = new Point2D.Double(2,2);
			l.positionChanged(oldP, newP);
			assertEquals(oldP.getY(), change1[0]);
			assertEquals(newP.getY(), change1[1]);
			assertNull(change2[0]);
			assertNull(change2[1]);
			change1[0] = null;
			change1[1] = null;
		}
		{
			SpriteListener l = sprite2.getListeners().iterator().next();
			Point2D oldP = new Point2D.Double(1,1);
			Point2D newP = new Point2D.Double(2,2);
			l.positionChanged(oldP, newP);
			assertNull(change1[0]);
			assertNull(change1[1]);
			assertEquals(oldP.getY(), change2[0]);
			assertEquals(newP.getY(), change2[1]);
			change2[0] = null;
			change2[1] = null;
		}
		
		op.removeValueListener(listener);
		
		assertEquals(0,sprite.getListeners().size());
		assertEquals(1,sprite2.getListeners().size());
		
		op.removeValueListener(listener2);
		
		assertEquals(0,sprite.getListeners().size());
		assertEquals(0,sprite2.getListeners().size());
	}
	
	public static class YposSetSprite extends AllBadSprite{
		private LinkedList<SpriteListener> listeners = new LinkedList<>();
		private double y;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#setScratchY(double)
		 */
		@Override
		public void setScratchY(double scratchY) {
			y = scratchY;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getScratchY()
		 */
		@Override
		public double getScratchY() {
			return y;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#addSpriteListener(com.shtick.utils.scratch.runner.core.SpriteListener)
		 */
		@Override
		public void addSpriteListener(SpriteListener listener) {
			listeners.add(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#removeSpriteListener(com.shtick.utils.scratch.runner.core.SpriteListener)
		 */
		@Override
		public void removeSpriteListener(SpriteListener listener) {
			listeners.remove(listener);
		}

		/**
		 * @return the listeners
		 */
		public LinkedList<SpriteListener> getListeners() {
			return listeners;
		}
	}
}
