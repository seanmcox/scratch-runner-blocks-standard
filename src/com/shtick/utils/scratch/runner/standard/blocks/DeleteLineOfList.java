/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

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
public class DeleteLineOfList implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "deleteLine:ofList:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT,DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		Object a0 = arguments[0];
		int n0;
		String s1 = (String)arguments[1];
		List list = context.getContextListByName(s1);
		if(a0 instanceof String) {
			if("last".equals(a0)) {
				n0 = list.getItemCount();
			}
			else if("all".equals(a0)) {
				while(list.getItemCount()>0)
					list.deleteItem(1);
				return null;
			}
			else {
				n0 = OpcodeUtils.getNumericValue(a0).intValue();
			}
		}
		else {
			n0 = OpcodeUtils.getNumericValue(a0).intValue();
		}
		try {
			list.deleteItem(n0);
		}
		catch(IndexOutOfBoundsException t) {
			System.err.println("WARNING: \""+getOpcode()+"\": Invalid index of list, "+s1+". "+t.getMessage());
			System.err.println(runner.getStackTrace());
		}
		return null;
	}

}
