package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.Rounded;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class RoundedTest {

	@Test
	void testOpcode() {
		Rounded op = new Rounded();
		assertEquals("rounded",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Rounded op = new Rounded();
		assertArrayEquals(new DataType[] {DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		Rounded op = new Rounded();

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5});
			assertEquals(2L,retval);
		}

		try {
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"1.5"});
			assertEquals(2L,retval);
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test","t"});
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
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}

	@Test
	void testInt() {
		AllBadSprite sprite = new AllBadSprite();
		Rounded op = new Rounded();

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {5L});
			assertEquals(5L,retval);
		}
	}

	@Test
	void testRoundUp() {
		AllBadSprite sprite = new AllBadSprite();
		Rounded op = new Rounded();

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2.6});
			assertEquals(3L,retval);
		}

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2.5});
			assertEquals(3L,retval);
		}
	}

	@Test
	void testRoundDown() {
		AllBadSprite sprite = new AllBadSprite();
		Rounded op = new Rounded();

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2.4});
			assertEquals(2L,retval);
		}
	}
}
