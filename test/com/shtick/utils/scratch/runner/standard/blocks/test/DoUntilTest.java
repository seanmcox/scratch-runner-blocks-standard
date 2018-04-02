package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;
import com.shtick.utils.scratch.runner.standard.blocks.DoUntil;

class DoUntilTest {

	@Test
	void testOpcode() {
		DoUntil op = new DoUntil();
		assertEquals("doUntil",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DoUntil op = new DoUntil();
		assertArrayEquals(new DataType[] {DataType.BOOLEAN,DataType.SCRIPT}, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		DoUntil op = new DoUntil();
		
		LinkedList<BlockTuple> subscript = new LinkedList<>();
		subscript.add(new BlockTuple() {
			@Override
			public Object[] toArray() {
				return new Object[] {"show"};
			}
			
			@Override
			public String getOpcode() {
				return "show";
			}
			
			@Override
			public List<Object> getArguments() {
				return new LinkedList<>();
			}
		});
		
		LinkedList<Object> arguments = new LinkedList<>();
		arguments.add(new Boolean(true));
		arguments.add(subscript);
		BlockTuple[] result = op.execute(arguments);
		assertEquals(4,result.length);
		assertTrue(result[0] instanceof TestBlockTuple);
		assertTrue(result[0].getArguments().get(0)==((TestBlockTuple)result[0]).getArguments().get(0));
		assertTrue(result[1] instanceof TrueJumpBlockTuple);
		assertEquals(4,result[1].getArguments().get(0));
		assertEquals(subscript.get(0),result[2]);
		assertTrue(result[3] instanceof BasicJumpBlockTuple);
		assertEquals(0,result[3].getArguments().get(0));
	}
}
