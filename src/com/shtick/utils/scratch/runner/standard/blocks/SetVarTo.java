/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class SetVarTo implements OpcodeAction {
	/**
	 * 
	 */
	public static final String OPCODE = "setVar:to:";

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return OPCODE;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.OBJECT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		String s0 = (String)arguments[0];
		Object val1 = arguments[1];
		if(s0.equals("growIdx")) {
			if(context instanceof ProcDef.ProcedureContext)
				System.out.println(((ProcDef.ProcedureContext)context).getProcName());
			if(arguments[1] instanceof BlockTuple) {
				System.out.println(((BlockTuple)arguments[1]).getOpcode());
				System.out.println(Arrays.toString(((BlockTuple)arguments[1]).getArguments().toArray()));
			}
			System.out.println("Setting "+s0+" to "+val1);
		}
		context.setContextVariableValueByName(s0, val1);
		return null;
	}

}
