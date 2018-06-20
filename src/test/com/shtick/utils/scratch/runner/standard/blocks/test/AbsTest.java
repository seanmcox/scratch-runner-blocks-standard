package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.Abs;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class AbsTest {

	@Test
	void testOpcode() {
		Abs op = new Abs();
		assertEquals("abs",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Abs op = new Abs();
		assertArrayEquals(new DataType[] {DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		Abs op = new Abs();

		{ // 0
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0L});
			assertEquals(0,((Number)retval).doubleValue());
		}

		{ // 1
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1L});
			assertEquals(1,((Number)retval).doubleValue());
		}

		{ // -1
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-1L});
			assertEquals(1,((Number)retval).doubleValue());
		}

		{ // 1.5
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5});
			assertEquals(1.5,((Number)retval).doubleValue());
		}

		{ // -1.5
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-1.5});
			assertEquals(1.5,((Number)retval).doubleValue());
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test"});
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
}
