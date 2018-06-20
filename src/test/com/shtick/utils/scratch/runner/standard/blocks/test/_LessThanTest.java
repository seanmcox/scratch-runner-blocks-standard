package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._LessThan;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class _LessThanTest {

	@Test
	void testOpcode() {
		_LessThan op = new _LessThan();
		assertEquals("<",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_LessThan op = new _LessThan();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_LessThan op = new _LessThan();

		{ // double < double, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertEquals(false,retval);
		}

		{ // double < double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0.5,1.5});
			assertEquals(true,retval);
		}

		{ // int < int, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,1});
			assertEquals(false,retval);
		}

		{ // int < int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,2});
			assertEquals(true,retval);
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
}
