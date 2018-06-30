/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoUntil implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doUntil";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.BOOLEAN,DataType.SCRIPT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(java.util.List<Object> arguments) {
		java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments.get(1);
		BlockTuple[] retval = new BlockTuple[subtuples.size()+3];
		retval[0] = new TestBlockTuple(arguments.get(0));
		retval[1] = new TrueJumpBlockTuple(retval.length);
		int i=2;
		for(BlockTuple subtuple:subtuples) {
			retval[i] = subtuple;
			i++;
		}
		retval[retval.length-1] = new BasicJumpBlockTuple(0);
		return retval;
	}

//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context, Object[] arguments) {
//		if(arguments.length!=2)
//			throw new IllegalArgumentException("2 arguments expected for "+getOpcode()+" opcode");
//		if(!OpcodeUtils.isEvaluable(arguments[0]))
//			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
//		if((!(arguments[1] instanceof java.util.List<?>))&&(arguments[1]!=null))
//			throw new IllegalArgumentException("List of BlockTuples expected as second argument for "+getOpcode()+", but "+arguments[0].getClass().getCanonicalName()+" found.");
//		java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments[1];
//		while(!(Boolean)arguments[0])
//			scriptRunner.runBlockTuples(context, subtuples);
//		return;
//	}

}
