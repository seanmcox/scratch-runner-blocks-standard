/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class _Times extends AbstractOpcodeValue {

	/**
	 */
	public _Times() {
		super(2, "*");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.NUMBER,DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Number n0 = (Number)arguments[0];
		Number n1 = (Number)arguments[0];
		if((n0 instanceof Integer)&&(n1 instanceof Integer))
			return n0.intValue()*n1.intValue();
		return n0.doubleValue()*n1.doubleValue();
	}

}
