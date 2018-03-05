/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * Not well defined in the Scratch wiki. See ProcDef for more details and references.
 * 
 * @author sean.cox
 *
 */
public class GetParam implements OpcodeValue {
	private static final String BLOCK_TYPE_REPORTER = "r";
	private static final String BLOCK_TYPE_STACK = " "; // Can't happen as I have heard described.
	private static final String BLOCK_TYPE_BOOLEAN = "b";

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "getParam";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		if(!(context instanceof ProcDef.ProcedureContext))
			throw new IllegalArgumentException(getOpcode()+" opcode executed in non-procedure context.");
		String s0 = (String)arguments[0];
		// arguments[2] identifies the block type, but it doesn't make a difference here.
		// String s1 = runner.getValue(context, arguments[1]).toString();
		ProcDef.ProcedureContext procedureContext = (ProcDef.ProcedureContext)context;
		Object retval = procedureContext.getParameterValueByName(s0);
		if(retval == null)
			return "";
		return retval;
	}
}
