package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._DividedBy;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class _DividedByTest {

	@Test
	void testOpcode() {
		_DividedBy op = new _DividedBy();
		assertEquals("/",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_DividedBy op = new _DividedBy();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_DividedBy op = new _DividedBy();

		{ // double / double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertTrue(retval instanceof Double);
			assertEqualWithinMargin(3.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // int / int
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2L,1L});
			assertTrue(retval instanceof Long);
			assertEqualWithinMargin(2.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // double / double w/remainder
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,1.1});
			assertTrue(retval instanceof Double);
			assertEqualWithinMargin(1.363636,((Number)retval).doubleValue(),0.00001);
		}

		{ // int / int w/remainder
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1L,2L});
			assertTrue(retval instanceof Double);
			assertEqualWithinMargin(0.5,((Number)retval).doubleValue(),0.00001);
		}
		
		{ // int NaN
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {500,0});
			assertEquals("NaN",retval);
		}
		
		{ // double NaN
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.0});
			assertEquals("NaN",retval);
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",0});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0,"test"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null, 0});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0, null});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}

	private static void assertEqualWithinMargin(double expected, double actual, double margin) {
		assertTrue(actual>(expected-margin),"Actual value, "+actual+" not greater than lower bound, "+(expected-margin));
		assertTrue(actual<(expected+margin),"Actual value, "+actual+" not less than upper bound, "+(expected+margin));
	}
}
