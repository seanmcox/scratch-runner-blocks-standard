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
public class _DividedBy extends AbstractOpcodeValue {

	/**
	 * 
	 */
	public _DividedBy() {
		super(2, "/");
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
		Number n1 = (Number)arguments[1];
		if((n0 instanceof Long)&&(n1 instanceof Long)&&((n0.longValue()%n1.longValue())==0)) {
			if(n1.intValue()==0)
				return "NaN";
			return n0.longValue()/n1.longValue();
		}
		Double retval =  n0.doubleValue()/n1.doubleValue();
		if(retval.isNaN())
			return "NaN";
		return retval;
	}
}
