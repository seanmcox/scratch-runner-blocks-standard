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
public class ListContains implements OpcodeValue {
	private HashMap<ValueListener,ListListener> listeners = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "list:contains:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.OBJECT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		String s0 = (String)arguments[0];
		Object a1 = arguments[1];
		List list = context.getContextListByName(s0);
		for(int i=1;i<=list.getItemCount();i++) {
			Object item = list.getItem(i);
			if(OpcodeUtils.equals(a1, item))
				return true;
		}
		return false;
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
		String s0 = (String)arguments[0];
		final List list = context.getContextListByName(s0);
		ListListener listListener = new ListListener() {
			@Override
			public void itemUpdated(int index, Object oldValue, Object newValue, List list) {}
			
			@Override
			public void itemRemoved(int index, Object value, List list) {
				int c = list.getItemCount();
				valueListener.valueUpdated(c+1, c);
			}
			
			@Override
			public void itemAdded(int index, Object value, List list) {
				int c = list.getItemCount();
				valueListener.valueUpdated(c-1, c);
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
		String s0 = (String)arguments[0];
		List list = context.getContextListByName(s0);
		list.removeListListener(listeners.get(valueListener));
		listeners.remove(valueListener);
	}
}
