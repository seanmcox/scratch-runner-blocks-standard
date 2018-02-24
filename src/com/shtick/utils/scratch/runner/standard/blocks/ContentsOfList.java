/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.ListListener;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class ContentsOfList implements OpcodeValue {
	private HashMap<ValueListener,ListListener> listeners = new HashMap<>();

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
		Object[] contents = list.getContents();
		for(Object item:contents) {
			if(item.toString().length()>0) {
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

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		final Object[] arguments = valueListener.getArguments();
		ScriptContext context = valueListener.getScriptContext();
		if(arguments.length!=1)
			throw new IllegalArgumentException("1 argument expected for "+getOpcode());
		if(!OpcodeUtils.isEvaluable(arguments[0]))
			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
		String s0 = OpcodeUtils.getStringValue(arguments[0]);
		final List list = context.getContextListByName(s0);
		ListListener listListener = new ListListener() {
			private String oldValue = getContents(list);
			
			private void update() {
				String newValue = getContents(list);
				valueListener.valueUpdated(oldValue, newValue);
				oldValue = newValue;
			}
			
			@Override
			public void itemUpdated(int index, Object oldValue, Object newValue, List list) {
				update();
			}
			
			@Override
			public void itemRemoved(int index, Object value, List list) {
				update();
			}
			
			@Override
			public void itemAdded(int index, Object value, List list) {
				update();
			}
		};
		list.addListListener(listListener);
		listeners.put(valueListener,listListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		if(!listeners.containsKey(valueListener))
			return;
		Object[] arguments = valueListener.getArguments();
		ScriptContext context = valueListener.getScriptContext();
		if(arguments.length!=1)
			throw new IllegalArgumentException("1 argument expected for "+getOpcode());
		if(!OpcodeUtils.isEvaluable(arguments[0]))
			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
		String s0 = OpcodeUtils.getStringValue(arguments[0]);
		List list = context.getContextListByName(s0);
		list.removeListListener(listeners.get(valueListener));
		listeners.remove(valueListener);
	}

}
