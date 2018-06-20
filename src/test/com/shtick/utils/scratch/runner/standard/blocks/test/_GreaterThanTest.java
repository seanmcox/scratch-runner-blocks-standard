package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._GreaterThan;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class _GreaterThanTest {

	@Test
	void testOpcode() {
		_GreaterThan op = new _GreaterThan();
		assertEquals(">",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_GreaterThan op = new _GreaterThan();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_GreaterThan op = new _GreaterThan();

		{ // double > double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertEquals(true,retval);
		}

		{ // double > double, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0.5,1.5});
			assertEquals(false,retval);
		}

		{ // int > int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,1});
			assertEquals(true,retval);
		}

		{ // int > int, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,2});
			assertEquals(false,retval);
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
