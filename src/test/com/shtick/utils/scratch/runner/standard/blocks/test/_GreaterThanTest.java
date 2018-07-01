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
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.OBJECT}, op.getArgumentTypes());
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
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",0});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0,"test"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A","B"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"B","A"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A","A"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AB"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AB","AA"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AA"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AA","AAA"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"AAA","AA"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A",true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A",false});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"A"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"A"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"G",true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"G",false});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"G"});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"G"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Z",true});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Z",false});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,"Z"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"Z"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,false});
			assertEquals(true,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,true});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,false});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"-22.0","-10.5"});
			assertEquals(false,retval);
		}
		
		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"-10.5","-22.0"});
			assertEquals(true,retval);
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
