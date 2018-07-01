/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class _GreaterThan extends AbstractOpcodeValue {
	/**
	 * 
	 */
	public static final String OPCODE = ">";

	/**
	 */
	public _GreaterThan() {
		super(2, OPCODE);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT,DataType.OBJECT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Object a0 = arguments[0];
		Object a1 = arguments[1];
		if(((a0 instanceof String)&&(!((String)a0).matches("^-?[0-9]+(\\.[0-9]+)*$")))||((a1 instanceof String)&&(!((String)a1).matches("^-?[0-9]+(\\.[0-9]+)*$")))) {
			String s0 = OpcodeUtils.getStringValue(a0);
			String s1 = OpcodeUtils.getStringValue(a1);
			return OpcodeUtils.compareStrings(s0,s1)>0;
		}
		Number n0 = OpcodeUtils.getNumericValue(a0);
		Number n1 = OpcodeUtils.getNumericValue(a1);
		if((n0 instanceof Long)&&(n1 instanceof Long))
			return n0.longValue()>n1.longValue();
		return n0.doubleValue()>n1.doubleValue();
	}

}
