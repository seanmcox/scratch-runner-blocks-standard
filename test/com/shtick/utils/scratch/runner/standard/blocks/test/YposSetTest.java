package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.YposSet;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class YposSetTest {

	@Test
	void testOpcode() {
		YposSet op = new YposSet();
		assertEquals("ypos:",op.getOpcode());
	}

	@Test
	void testArgs() {
		YposSetSprite sprite = new YposSetSprite();
		YposSet op = new YposSet();

		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {10});
			assertEquals(10,sprite.getScratchY());
		}

		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-10});
			assertEquals(-10,sprite.getScratchY());
		}

		try{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item","test"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}
	
	public static class YposSetSprite extends AllBadSprite{
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
	}
}
