/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Random;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class RandomFromTo extends AbstractOpcodeValue {
	private Random random = new Random();

	/**
	 */
	public RandomFromTo() {
		super(2, "randomFrom:to:");
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
		if((n0 instanceof Long)&&(n1 instanceof Long)) {
			long min=Math.min(n0.longValue(), n1.longValue());
			long max=Math.max(n0.longValue(), n1.longValue());
			return min+(long)(random.nextDouble()*(max-min+1));
		}
		double min=Math.min(n0.doubleValue(), n1.doubleValue());
		double max=Math.max(n0.doubleValue(), n1.doubleValue());
		return (min+random.nextDouble()*(max-min));
	}

}
