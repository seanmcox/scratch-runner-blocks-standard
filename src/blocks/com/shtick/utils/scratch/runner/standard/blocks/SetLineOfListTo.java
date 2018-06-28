/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Random;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class SetLineOfListTo implements OpcodeAction {
	private Random random = new Random();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "setLine:ofList:to:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT,DataType.STRING,DataType.OBJECT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Object a0 = arguments[0];
		int n0;
		String s1 = (String)arguments[1];
		Object arg2 = arguments[2];
		List list = context.getContextListByName(s1);
		if(a0 instanceof String) {
			if("last".equals(a0)) {
				n0 = list.getItemCount();
			}
			else if("random".equals(a0)) {
				n0 = random.nextInt(list.getItemCount())+1;
			}
			else {
				n0 = OpcodeUtils.getNumericValue(a0).intValue();
			}
		}
		else {
			n0 = OpcodeUtils.getNumericValue(a0).intValue();
		}
		try {
			list.setItem(arg2, n0);
		}
		catch(IndexOutOfBoundsException t) {
			System.err.println("WARNING: \""+getOpcode()+"\": Invalid index of list, "+s1+". "+t.getMessage());
			System.err.println(runner.getStackTrace());
		}
		return null;
	}

}
