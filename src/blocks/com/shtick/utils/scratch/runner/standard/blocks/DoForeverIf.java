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
public class DoForeverIf implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doForeverIf";
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
		retval[1] = new FalseJumpBlockTuple(retval.length-1);
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
//		while(!scriptRunner.isStopFlagged()) {
//			Object value = arguments[0];
//			if(!(value instanceof Boolean))
//				throw new IllegalArgumentException("Boolean condition expected.");
//			if(((Boolean)value).booleanValue()) {
//				scriptRunner.runBlockTuples(context, (java.util.List<BlockTuple>)arguments[1]);
//			}
//		}
//		return;
//	}

}
