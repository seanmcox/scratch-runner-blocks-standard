/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.FalseJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;

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
		BlockTuple[] retval = new BlockTuple[subtuples1.size()+subtuples2.size()+3];
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

//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
//			Object[] arguments) {
//		if(arguments.length!=3)
//			throw new IllegalArgumentException("3 arguments expected for "+getOpcode());
//		if(!OpcodeUtils.isEvaluable(arguments[0]))
//			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
//		if((!(arguments[1] instanceof java.util.List<?>))&&(arguments[1]!=null))
//			throw new IllegalArgumentException("List of BlockTuples expected, but "+arguments[0].getClass().getCanonicalName()+" found.");
//		if((!(arguments[2] instanceof java.util.List<?>))&&(arguments[2]!=null))
//			throw new IllegalArgumentException("List of BlockTuples expected, but "+arguments[0].getClass().getCanonicalName()+" found.");
//		Object value = arguments[0];
//		if(!(value instanceof Boolean))
//			throw new IllegalArgumentException("Boolean condition expected.\nFound: "+value+"\nEvaluated from: "+arguments[0]+"-"+((arguments[0] instanceof BlockTuple)?((BlockTuple)arguments[0]).getOpcode():""));
//		if(((Boolean)value).booleanValue()) {
//			scriptRunner.runBlockTuples(context, (java.util.List<BlockTuple>)arguments[1]);
//		}
//		else {
//			scriptRunner.runBlockTuples(context, (java.util.List<BlockTuple>)arguments[2]);
//		}
//	}

}
