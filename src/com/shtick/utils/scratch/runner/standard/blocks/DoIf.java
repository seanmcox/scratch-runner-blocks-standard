/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.FalseJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoIf implements OpcodeControl{

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doIf";
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
		if(subtuples==null) {
			System.err.println("WARNING: \""+getOpcode()+"\": doIf found with empty execution block.");
			return new BlockTuple[0];
		}
		BlockTuple[] retval = new BlockTuple[subtuples.size()+2];
		retval[0] = new TestBlockTuple(arguments.get(0));
		retval[1] = new FalseJumpBlockTuple(retval.length);
		int i=2;
		for(BlockTuple subtuple:subtuples) {
			retval[i] = subtuple;
			i++;
		}
		return retval;
	}

//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
//			Object[] arguments) {
//		if(arguments.length!=2)
//			throw new IllegalArgumentException("2 arguments expected for "+getOpcode());
//		if(!OpcodeUtils.isEvaluable(arguments[0]))
//			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
//		if((!(arguments[1] instanceof java.util.List<?>))&&(arguments[1]!=null))
//			throw new IllegalArgumentException("List of BlockTuples expected, but "+arguments[0].getClass().getCanonicalName()+" found.");
//		Object value = arguments[0];
//		if(!(value instanceof Boolean))
//			throw new IllegalArgumentException("Boolean condition expected.");
//		if(((Boolean)value).booleanValue()) {
//			java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments[1];
//			scriptRunner.runBlockTuples(context, subtuples);
//		}
//	}

}
