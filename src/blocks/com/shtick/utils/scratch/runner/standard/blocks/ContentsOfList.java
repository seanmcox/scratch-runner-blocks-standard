/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class ContentsOfList implements OpcodeValue {
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "contentsOfList:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		List list = context.getContextListByName((String)arguments[0]);
		return getContents(list);
	}
	
	private static String getContents(List list) {
		boolean all1 = true;
		if(list==null)
			return "";
		Object[] contents = list.getContents();
		for(Object item:contents) {
			if(item.toString().length()>1) {
				all1 = false;
				break;
			}
		}
		String retval = "";
		if(all1) {
			for(Object item:contents)
				retval+=item.toString();
			return retval;
		}
		for(Object item:contents) {
			if(retval.length()>0)
				retval+=" ";
			retval+=item.toString();
		}
		return retval;
	}
}
