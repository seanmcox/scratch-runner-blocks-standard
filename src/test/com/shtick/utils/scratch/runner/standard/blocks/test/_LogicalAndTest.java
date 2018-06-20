package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._LogicalAnd;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class _LogicalAndTest {

	@Test
	void testOpcode() {
		_LogicalAnd op = new _LogicalAnd();
		assertEquals("&",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_LogicalAnd op = new _LogicalAnd();
		assertArrayEquals(new DataType[] {DataType.BOOLEAN,DataType.BOOLEAN}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_LogicalAnd op = new _LogicalAnd();

		{ // true & true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,true});
			assertEquals(true,retval);
		}

		{ // true & false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,false});
			assertEquals(false,retval);
		}

		{ // false & true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,true});
			assertEquals(false,retval);
		}

		{ // false & false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,false});
			assertEquals(false,retval);
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,true});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,1});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",false});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"test"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null, false});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false, null});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}
}
