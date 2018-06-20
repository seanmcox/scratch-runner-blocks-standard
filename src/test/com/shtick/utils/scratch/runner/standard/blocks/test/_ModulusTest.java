package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._Modulus;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class _ModulusTest {

	@Test
	void testOpcode() {
		_Modulus op = new _Modulus();
		assertEquals("%",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_Modulus op = new _Modulus();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_Modulus op = new _Modulus();

		{ // double % double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertEqualWithinMargin(0.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // double % double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.7,0.5});
			assertEqualWithinMargin(0.2,((Number)retval).doubleValue(),0.00001);
		}

		{ // double % double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-1.7,0.5});
			assertEqualWithinMargin(0.3,((Number)retval).doubleValue(),0.00001);
		}

		{ // int % int
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,1});
			assertEqualWithinMargin(0.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // int % int
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {12,10});
			assertEqualWithinMargin(2.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // int % int
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-12,10});
			assertEqualWithinMargin(8.0,((Number)retval).doubleValue(),0.00001);
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
