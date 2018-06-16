package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.ChangeLocalVarByBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.SetLocalVarBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;
import com.shtick.utils.scratch.runner.standard.blocks.DoRepeat;

class DoRepeatTest {

	@Test
	void testOpcode() {
		DoRepeat op = new DoRepeat();
		assertEquals("doRepeat",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DoRepeat op = new DoRepeat();
		assertArrayEquals(new DataType[] {DataType.NUMBER,DataType.SCRIPT}, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		DoRepeat op = new DoRepeat();
		
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
		arguments.add(4L);
		arguments.add(subscript);
		BlockTuple[] result = op.execute(arguments);
		assertEquals(6+subscript.size(),result.length);
		assertTrue(result[0] instanceof SetLocalVarBlockTuple);
		assertTrue(result[1] instanceof SetLocalVarBlockTuple);
		assertEquals(arguments.get(0),((SetLocalVarBlockTuple)result[1]).getArguments().get(1));
		assertTrue(result[2] instanceof TestBlockTuple);
		assertTrue(result[3] instanceof TrueJumpBlockTuple);
		assertEquals(6+subscript.size(),result[3].getArguments().get(0));
		assertEquals(subscript.get(0),result[4]);
		
		assertTrue(result[5] instanceof ChangeLocalVarByBlockTuple);
		assertEquals(1,result[5].getArguments().get(1));
		assertTrue(result[6] instanceof BasicJumpBlockTuple);
		assertEquals(2,result[6].getArguments().get(0));
	}
}
