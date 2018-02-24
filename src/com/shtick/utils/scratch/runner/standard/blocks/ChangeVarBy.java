/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class ChangeVarBy implements OpcodeAction {
	/**
	 * 
	 */
	public static final String OPCODE = "changeVar:by:";

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
		return new DataType[] {DataType.STRING,DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(arguments.length!=2)
			throw new IllegalArgumentException("2 argument expected for "+getOpcode()+" opcode");
		if(!OpcodeUtils.isEvaluable(arguments[0]))
			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
		if(!OpcodeUtils.isEvaluable(arguments[1]))
			throw new IllegalArgumentException("The second argument for opcode, "+getOpcode()+", must be evaluable.");
		String s0 = (String)arguments[0];
		Number n1 = (Number)arguments[1];
		Number oldValue = OpcodeUtils.getNumericValue(context.getContextVariableValueByName(s0));
		if(s0.equals("growIdx")) {
			if(context instanceof ProcDef.ProcedureContext)
				System.out.println(((ProcDef.ProcedureContext)context).getProcName());
			if(arguments[1] instanceof BlockTuple) {
				System.out.println(((BlockTuple)arguments[1]).getOpcode());
				System.out.println(Arrays.toString(((BlockTuple)arguments[1]).getArguments()));
			}
			System.out.println("Changing "+s0+" by "+n1+" (old value: "+oldValue+")");
		}
		if((oldValue instanceof Integer)&&(n1 instanceof Integer))
			context.setContextVariableValueByName(s0, oldValue.intValue()+n1.intValue());
		context.setContextVariableValueByName(s0, oldValue.doubleValue()+n1.doubleValue());
	}

}
