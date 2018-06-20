package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.ConcatenateWith;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class ConcatenateWithTest {

	@Test
	void testOpcode() {
		ConcatenateWith op = new ConcatenateWith();
		assertEquals("concatenate:with:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ConcatenateWith op = new ConcatenateWith();
		assertArrayEquals(new DataType[] {DataType.STRING,DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		ConcatenateWith op = new ConcatenateWith();

		{ // Basic test
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Hello","World"});
			assertTrue(retval instanceof String);
			assertEquals("HelloWorld",retval);
		}

		{ // Alternate
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Goodbye","Me"});
			assertTrue(retval instanceof String);
			assertEquals("GoodbyeMe",retval);
		}

		{ // Empty concat
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"","I"});
			assertTrue(retval instanceof String);
			assertEquals("I", retval);
		}

		{ // Inverse empty concat
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"You",""});
			assertTrue(retval instanceof String);
			assertEquals("You", retval);
		}
		
		try { // Non-String 1st param
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"Egg"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try { // Non-String 2nd param
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Egg",1});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}
}
