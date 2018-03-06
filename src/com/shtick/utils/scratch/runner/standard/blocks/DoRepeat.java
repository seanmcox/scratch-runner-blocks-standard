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
		BlockTuple[] retval = new BlockTuple[subtuples.size()+5];
		BlockTuple readVar = new ReadLocalVarBlockTuple(0);
		BlockTuple setVar = new SetLocalVarBlockTuple(0, 1);
		
		BlockTuple test = new BlockTuple() {
			java.util.List<Object> args = Collections.unmodifiableList(Arrays.asList(readVar,arguments.get(0)));
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
		retval[0] = setVar;
		retval[1] = new TestBlockTuple(test);
		retval[2] = new TrueJumpBlockTuple(retval.length);
		int i=3;
		for(BlockTuple subtuple:subtuples) {
			retval[i] = subtuple;
			i++;
		}
		retval[retval.length-2] = incrementVar;
		retval[retval.length-1] = new BasicJumpBlockTuple(1);
		return retval;
	}
}
