/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.InvalidScriptDefinitionException;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.JumpBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoForever implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doForever";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.SCRIPT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(java.util.List<Object> arguments) {
		java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments.get(0);
		BlockTuple[] retval = subtuples.toArray(new BlockTuple[subtuples.size()+1]);
		retval[retval.length-1] = new BasicJumpBlockTuple(0);
		return retval;
	}
//
//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context, Object[] arguments) {
//		java.util.List<BlockTuple> subtuples = ((ScriptTuple)arguments[0]).getBlockTuples();
//		try {
//			while(!scriptRunner.isStopFlagged())
//				scriptRunner.runBlockTuples(context, subtuples);
//		}
//		catch(InvalidScriptDefinitionException t) {
//			throw new IllegalArgumentException(t);
//		}
//		return;
//	}
}
