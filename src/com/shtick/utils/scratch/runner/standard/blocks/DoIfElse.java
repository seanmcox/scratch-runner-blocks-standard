/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.FalseJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoIfElse implements OpcodeControl{

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doIfElse";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.BOOLEAN,DataType.SCRIPT,DataType.SCRIPT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(java.util.List<Object> arguments) {
		java.util.List<BlockTuple> subtuples1 = (java.util.List<BlockTuple>)arguments.get(1);
		java.util.List<BlockTuple> subtuples2 = (java.util.List<BlockTuple>)arguments.get(2);
		BlockTuple[] retval;
		if(subtuples1==null) {
			if(subtuples2==null)
				return new BlockTuple[0];
			retval = new BlockTuple[subtuples2.size()+2];
			retval[0] = new TestBlockTuple(arguments.get(0));
			retval[1] = new TrueJumpBlockTuple(retval.length);
			int i=2;
			for(BlockTuple subtuple:subtuples2) {
				retval[i] = subtuple;
				i++;
			}
			return retval;
		}
		if(subtuples2==null) {
			retval = new BlockTuple[subtuples1.size()+2];
			retval[0] = new TestBlockTuple(arguments.get(0));
			retval[1] = new FalseJumpBlockTuple(retval.length);
			int i=2;
			for(BlockTuple subtuple:subtuples1) {
				retval[i] = subtuple;
				i++;
			}
			return retval;
		}
		retval = new BlockTuple[subtuples1.size()+subtuples2.size()+3];
		retval[0] = new TestBlockTuple(arguments.get(0));
		retval[1] = new FalseJumpBlockTuple(subtuples1.size()+3);
		int i=2;
		for(BlockTuple subtuple:subtuples1) {
			retval[i] = subtuple;
			i++;
		}
		retval[i] = new BasicJumpBlockTuple(retval.length);
		i++;
		for(BlockTuple subtuple:subtuples2) {
			retval[i] = subtuple;
			i++;
		}
		return retval;
	}
}
