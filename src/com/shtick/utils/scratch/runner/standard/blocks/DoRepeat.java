/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;
import java.util.Collections;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.ChangeLocalVarByBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.ReadLocalVarBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.SetLocalVarBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoRepeat implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doRepeat";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.NUMBER,DataType.SCRIPT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(java.util.List<Object> arguments) {
		java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments.get(1);
		BlockTuple[] retval = new BlockTuple[subtuples.size()+6];
		BlockTuple readVar0 = new ReadLocalVarBlockTuple(0);
		BlockTuple readVar1 = new ReadLocalVarBlockTuple(1);
		BlockTuple setVar0 = new SetLocalVarBlockTuple(0, 1);
		BlockTuple setVar1 = new SetLocalVarBlockTuple(1, arguments.get(0));
		
		BlockTuple test = new BlockTuple() {
			java.util.List<Object> args = Collections.unmodifiableList(Arrays.asList(readVar0,readVar1));
			@Override
			public Object[] toArray() {
				return null;
			}
			@Override
			public String getOpcode() {
				return _GreaterThan.OPCODE;
			}
			@Override
			public java.util.List<Object> getArguments() {
				return args;
			}
		};
		BlockTuple incrementVar = new ChangeLocalVarByBlockTuple(0, 1);
		retval[0] = setVar0;
		retval[1] = setVar1;
		retval[2] = new TestBlockTuple(test);
		retval[3] = new TrueJumpBlockTuple(retval.length);
		int i=4;
		for(BlockTuple subtuple:subtuples) {
			retval[i] = subtuple;
			i++;
		}
		retval[retval.length-2] = incrementVar;
		retval[retval.length-1] = new BasicJumpBlockTuple(2);
		return retval;
	}
}
