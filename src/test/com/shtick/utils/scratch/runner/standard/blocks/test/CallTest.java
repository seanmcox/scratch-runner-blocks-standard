package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.Call;
import com.shtick.utils.scratch.runner.standard.blocks.ProcDef;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class CallTest {

	@Test
	void testOpcode() {
		Call op = new Call(new ProcDef());
		assertEquals("call",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Call op = new Call(new ProcDef());
		assertArrayEquals(new DataType[] {DataType.STRING,DataType.OBJECTS}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		AllBadRuntime runtime = new AllBadRuntime();
		AllBadSprite sprite = new AllBadSprite();
		
		Stack<String> procNameStack = new Stack<>();
		Stack<Object[]> paramsStack = new Stack<>();
		OpcodeSubaction[] callRetval = new OpcodeSubaction[1];
		ProcDef procDef = new ProcDef() {

			/* (non-Javadoc)
			 * @see com.shtick.utils.scratch.runner.standard.blocks.ProcDef#call(com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.String, java.lang.Object[])
			 */
			@Override
			public OpcodeSubaction call(ScriptContext context, String procName, Object[] params) {
				procNameStack.push(procName);
				paramsStack.push(params);
				return callRetval[0];
			}
		};
		Call op = new Call(procDef);
		assertEquals(0,procNameStack.size());
		assertEquals(0,paramsStack.size());

		{
			Object[] params = new Object[] {};
			callRetval[0] = null;
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"hello_world", params});
			assertEquals(callRetval[0],result);
			assertEquals(1,procNameStack.size());
			assertEquals(1,paramsStack.size());
			assertEquals("hello_world",procNameStack.peek());
			assertEquals(params,paramsStack.peek());
		}

		{
			Object[] params = new Object[] {"value"};
			callRetval[0] = new OpcodeSubaction() {
				
				@Override
				public boolean shouldYield() {
					return false;
				}
				
				@Override
				public boolean isSubscriptAtomic() {
					return false;
				}
				
				@Override
				public Type getType() {
					return null;
				}
				
				@Override
				public ScriptTuple getSubscript() {
					return null;
				}
			};
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"hw", params});
			assertEquals(callRetval[0],result);
			assertEquals(2,procNameStack.size());
			assertEquals(2,paramsStack.size());
			assertEquals("hw",procNameStack.peek());
			assertEquals(params,paramsStack.peek());
		}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"hw"});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {null, new Object[]{}});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"hw","not array"});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
	}
}
