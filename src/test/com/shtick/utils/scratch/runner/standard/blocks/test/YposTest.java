package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.SpriteListener;
import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.Ypos;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class YposTest {

	@Test
	void testOpcode() {
		Ypos op = new Ypos();
		assertEquals("ypos",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Ypos op = new Ypos();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
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
