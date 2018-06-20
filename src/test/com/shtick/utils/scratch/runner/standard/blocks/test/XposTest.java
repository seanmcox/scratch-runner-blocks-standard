package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.SpriteListener;
import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.Xpos;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class XposTest {

	@Test
	void testOpcode() {
		Xpos op = new Xpos();
		assertEquals("xpos",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Xpos op = new Xpos();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		XposSetSprite sprite = new XposSetSprite();
		Xpos op = new Xpos();

		{
			sprite.setScratchX(10);
			Object value = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertEquals(new Double(10),value);
		}

		{
			sprite.setScratchX(-10);
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
	
	public static class XposSetSprite extends AllBadSprite{
		private LinkedList<SpriteListener> listeners = new LinkedList<>();
		private double x;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#setScratchX(double)
		 */
		@Override
		public void setScratchX(double scratchX) {
			x = scratchX;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getScratchX()
		 */
		@Override
		public double getScratchX() {
			return x;
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
