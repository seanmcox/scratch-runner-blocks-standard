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
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.OBJECT}, op.getArgumentTypes());
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
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",0});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0,"test"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A","B"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"B","A"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A","A"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AB"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AB","AA"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AA"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AAA"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AAA","AA"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A",true});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A",false});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"A"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"A"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"G",true});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"G",false});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"G"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"G"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Z",true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Z",false});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"Z"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"Z"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,true});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,false});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,false});
			assertEquals(false,retval);
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
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
