package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.RandomFromTo;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class RandomFromToTest {

	@Test
	void testOpcode() {
		RandomFromTo op = new RandomFromTo();
		assertEquals("randomFrom:to:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		RandomFromTo op = new RandomFromTo();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		RandomFromTo op = new RandomFromTo();

		{ // double==0, double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0.0,4.5});
			assertTrue(retval instanceof Double);
			assertTrue(((Number)retval).doubleValue()>=0.0);
			assertTrue(((Number)retval).doubleValue()<=4.5);
		}

		{ // double>0, double
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {5.5,10.5});
			assertTrue(retval instanceof Double);
			assertTrue(((Number)retval).doubleValue()>=5.5);
			assertTrue(((Number)retval).doubleValue()<=10.5);
		}

		{ // long==0, long
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0L,4L});
			assertTrue(retval instanceof Long);
			assertTrue(((Number)retval).longValue()>=0);
			assertTrue(((Number)retval).longValue()<=4);
		}

		{ // long>0, long
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {5L,10L});
			assertTrue(retval instanceof Long);
			assertTrue(((Number)retval).longValue()>=5);
			assertTrue(((Number)retval).longValue()<=10);
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
