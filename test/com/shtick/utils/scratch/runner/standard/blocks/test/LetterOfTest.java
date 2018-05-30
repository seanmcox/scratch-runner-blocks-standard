package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.LetterOf;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class LetterOfTest {

	@Test
	void testOpcode() {
		LetterOf op = new LetterOf();
		assertEquals("letter:of:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		LetterOf op = new LetterOf();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		LetterOf op = new LetterOf();

		{ // First char
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals("H",(String)retval);
		}

		{ // Other char
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {6,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals(" ", (String)retval);
		}

		{ // 0-char
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals("", (String)retval);
		}

		{ // Negative char
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {-1,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals("", (String)retval);
		}

		{ // Too big char
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {12,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals("", (String)retval);
		}

		{ // Floating point resolution
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,"Hello World"});
			assertTrue(retval instanceof String);
			assertEquals("H",(String)retval);
		}
		
		try { // Non-String 2nd param
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,1});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Hello World","Hello World"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null, "Hello World"});
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
