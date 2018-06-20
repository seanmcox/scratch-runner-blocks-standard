package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.XposSet;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class XposSetTest {

	@Test
	void testOpcode() {
		XposSet op = new XposSet();
		assertEquals("xpos:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		XposSet op = new XposSet();
		assertArrayEquals(new DataType[] {DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		XposSetSprite sprite = new XposSetSprite();
		XposSet op = new XposSet();

		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {10});
			assertEquals(10,sprite.getScratchX());
		}

		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-10});
			assertEquals(-10,sprite.getScratchX());
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item"});
			assertEquals(0,sprite.getScratchX());
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
	
	public static class XposSetSprite extends AllBadSprite{
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
	}
}
