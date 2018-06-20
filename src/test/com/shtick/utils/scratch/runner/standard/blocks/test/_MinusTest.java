package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._Minus;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class _MinusTest {

	@Test
	void testOpcode() {
		_Minus op = new _Minus();
		assertEquals("-",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_Minus op = new _Minus();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_Minus op = new _Minus();

		{ // double - double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertEqualWithinMargin(1.0,((Number)retval).doubleValue(),0.00001);
		}

		{ // int - int
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,1});
			assertEqualWithinMargin(1.0,((Number)retval).doubleValue(),0.00001);
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
