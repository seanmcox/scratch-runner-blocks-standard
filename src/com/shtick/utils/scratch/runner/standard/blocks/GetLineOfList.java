/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;

import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class GetLineOfList implements OpcodeValue {
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "getLine:ofList:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT,DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Object arg0 = arguments[0];
		int n0;
		String s1 = (String)arguments[1];
		List list = context.getContextListByName(s1);
		if(arg0 instanceof String) {
			if("last".equals(arg0)) {
				n0 = list.getItemCount();
			}
			else if("random".equals(arg0)) {
				n0 = (int)(list.getItemCount()*Math.random());
				n0++;
			}
			else {
				n0 = OpcodeUtils.getNumericValue(arg0).intValue();
			}
		}
		else {
			n0 = OpcodeUtils.getNumericValue(arg0).intValue();
		}
		try {
			return list.getItem(n0);
		}
		catch(IndexOutOfBoundsException t) {
			System.err.println("WARNING: \""+getOpcode()+"\": Invalid index of list, "+s1+". "+t.getMessage());
			return "";
		}
	}
}
