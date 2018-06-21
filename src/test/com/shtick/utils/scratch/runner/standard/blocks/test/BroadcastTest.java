package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.standard.blocks.Broadcast;
import com.shtick.utils.scratch.runner.standard.blocks.WhenIReceive;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class BroadcastTest {

	@Test
	void testOpcode() {
		Broadcast op = new Broadcast(new WhenIReceive());
		assertEquals("broadcast:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Broadcast op = new Broadcast(new WhenIReceive());
		assertArrayEquals(new DataType[] {DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		AllBadRuntime runtime = new AllBadRuntime();
		AllBadSprite sprite = new AllBadSprite();
		
		Stack<String> stack = new Stack<>();
		WhenIReceive whenIReceive = new WhenIReceive() {

			/* (non-Javadoc)
			 * @see com.shtick.utils.scratch.runner.standard.blocks.WhenIReceive#broadcast(java.lang.String)
			 */
			@Override
			public Set<ScriptTupleRunner> broadcast(String message) {
				stack.push(message);
				return null;
			}
			
		};
		Broadcast op = new Broadcast(whenIReceive);
		assertEquals(0,stack.size());

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"hello_world"});
			assertNull(result);
			assertEquals(1,stack.size());
			assertEquals("hello_world",stack.peek());
		}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {null});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
	}
}
