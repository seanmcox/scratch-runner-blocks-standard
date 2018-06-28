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
public class InsertAtOfList implements OpcodeAction {
	private Random random = new Random();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "insert:at:ofList:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT,DataType.OBJECT,DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Object a0 = arguments[0];
		Object a1 = arguments[1];
		int n1;
		String s2 = (String)arguments[2];
		List list = context.getContextListByName(s2);
		if(a1 instanceof String) {
			if("last".equals(a1)) {
				n1 = list.getItemCount()+1;
			}
			else if("random".equals(a1)) {
				n1 = random.nextInt(list.getItemCount())+1;
			}
			else {
				n1 = OpcodeUtils.getNumericValue(a1).intValue();
			}
		}
		else {
			n1 = OpcodeUtils.getNumericValue(a1).intValue();
		}
		try {
			list.addItem(a0, n1);
		}
		catch(IndexOutOfBoundsException t) {
			System.err.println("WARNING: \""+getOpcode()+"\": Invalid index of list, "+s2+". "+t.getMessage());
			System.err.println(runner.getStackTrace());
		}
		return null;
	}

}
