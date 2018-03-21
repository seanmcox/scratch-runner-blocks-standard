package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks._Equals;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class _EqualsTest {

	@Test
	void testOpcode() {
		_Equals op = new _Equals();
		assertEquals("=",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		_Equals op = new _Equals();
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.OBJECT}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		_Equals op = new _Equals();

		{ // double == double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,1.5});
			assertTrue((Boolean)retval);
		}

		{ // double == double, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1.5,0.5});
			assertFalse((Boolean)retval);
		}

		{ // int == int, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,2});
			assertTrue((Boolean)retval);
		}

		{ // int == int, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,1});
			assertFalse((Boolean)retval);
		}

		{ // string == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"2",2});
			assertTrue((Boolean)retval);
		}

		{ // string == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"1",2});
			assertFalse((Boolean)retval);
		}

		{ // int == string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,"2"});
			assertTrue((Boolean)retval);
		}

		{ // int == string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,"1"});
			assertFalse((Boolean)retval);
		}

		{ // 0-string == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",0});
			assertFalse((Boolean)retval);
		}

		{ // int == 0-string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0,"test"});
			assertFalse((Boolean)retval);
		}

		{ // string == double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"2.5",2.5});
			assertTrue((Boolean)retval);
		}

		{ // string == double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"1.5",2.5});
			assertFalse((Boolean)retval);
		}

		{ // double == string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2.5,"2.5"});
			assertTrue((Boolean)retval);
		}

		{ // double == string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2.5,"1.5"});
			assertFalse((Boolean)retval);
		}

		{ // 0-string == double, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test",0.0});
			assertFalse((Boolean)retval);
		}

		{ // double == 0-string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0.0,"test"});
			assertFalse((Boolean)retval);
		}

		{ // string == string, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test","test"});
			assertTrue((Boolean)retval);
		}

		{ // string == string, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"testification","test"});
			assertFalse((Boolean)retval);
		}

		{ // bool == bool, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,true});
			assertTrue((Boolean)retval);
		}

		{ // bool == bool, false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,false});
			assertFalse((Boolean)retval);
		}

		{ // true == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,1});
			assertTrue((Boolean)retval);
		}

		{ // true == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {true,0});
			assertFalse((Boolean)retval);
		}

		{ // false == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,0});
			assertTrue((Boolean)retval);
		}

		{ // false == int, true
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,1});
			assertFalse((Boolean)retval);
		}

		{ // false == string
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"0"});
			assertTrue((Boolean)retval);
		}

		{ // false == string
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {false,"false"});
			assertFalse((Boolean)retval);
		}

		{ // string == false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"0",false});
			assertTrue((Boolean)retval);
		}

		{ // string == false
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"false",false});
			assertFalse((Boolean)retval);
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1});
			fail("Exception expected.");
		}
		catch (Exception t) {
			
		}

// TODO Will need to edit a project file to determine what this should be.
//		try {
//			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null, 0});
//			fail("Exception expected.");
//		}
//		catch(Exception t) {
//			// Expected Result.
//		}
//		
//		try {
//			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {0, null});
//			fail("Exception expected.");
//		}
//		catch(Exception t) {
//			// Expected Result.
//		}
	}
}
