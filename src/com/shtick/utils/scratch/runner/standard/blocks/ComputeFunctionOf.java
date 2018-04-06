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
public class ComputeFunctionOf extends AbstractOpcodeValue {

	/**
	 */
	public ComputeFunctionOf() {
		super(2, "computeFunction:of:");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		String s0 = (String)arguments[0];
		Number n1 = (Number)arguments[1];
		switch(s0) {
		case "abs":
			if(n1.doubleValue()>0)
				return n1;
			if(n1 instanceof Long)
				return -n1.longValue();
			return -n1.doubleValue();
		case "sqrt":
			if(n1.doubleValue()<0)
				return Double.NaN;
			return Math.sqrt(n1.doubleValue());
		case "sin":
			return Math.sin(n1.doubleValue()*Math.PI/180);
		case "cos":
			return Math.cos(n1.doubleValue()*Math.PI/180);
		case "tan":
			return Math.tan(n1.doubleValue()*Math.PI/180);
		case "asin":
			return Math.asin(n1.doubleValue());
		case "acos":
			return Math.acos(n1.doubleValue());
		case "atan":
			return Math.atan(n1.doubleValue());
		case "e^":
			return Math.exp(n1.doubleValue());
		case "10^":
			return Math.pow(10, n1.doubleValue());
		case "ln":
			return Math.log(n1.doubleValue());
		case "log":
			return Math.log10(n1.doubleValue());
		case "floor":
			return (int)Math.floor(n1.doubleValue());
		case "ceiling":
			return (int)Math.ceil(n1.doubleValue());
		default:
			throw new IllegalArgumentException("Unrecognized function called: "+s0);
		}
	}

}
