/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Arrays;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.ListListener;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class GetLineOfList implements OpcodeValue {
	private HashMap<ValueListener,ListListener> listeners = new HashMap<>();

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
			if(!("_Level".equals(s1)||"tree".equals(s1))) {
				if(context instanceof ProcDef.ProcedureContext)
					System.out.println(((ProcDef.ProcedureContext)context).getProcName());
				if(arguments[0] instanceof BlockTuple) {
					System.out.println(((BlockTuple)arguments[0]).getOpcode());
					System.out.println(Arrays.toString(((BlockTuple)arguments[0]).getArguments()));
				}
				System.out.println(s1);
//				System.exit(1);
			}
			return "";
		}
	}


	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		final Object[] arguments = valueListener.getArguments();
		ScriptContext context = valueListener.getScriptContext();
		if(arguments.length!=2)
			throw new IllegalArgumentException("2 arguments expected for "+getOpcode());
		if(!OpcodeUtils.isEvaluable(arguments[0]))
			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
		if(!OpcodeUtils.isEvaluable(arguments[1]))
			throw new IllegalArgumentException("The second argument for opcode, "+getOpcode()+", must be evaluable.");
		final Number n0 = OpcodeUtils.getNumericValue(arguments[0]);
		String s1 = OpcodeUtils.getStringValue(arguments[1]);
		final List list = context.getContextListByName(s1);
		ListListener listListener = new ListListener() {
			private Object oldValue = list.getItem(n0.intValue());
			
			private void update() {
				Object newValue = list.getItem(n0.intValue());
				valueListener.valueUpdated(oldValue, newValue);
				oldValue = newValue;
			}
			
			@Override
			public void itemUpdated(int index, Object oldValue, Object newValue, List list) {
				if(index==n0.intValue())
					update();
			}
			
			@Override
			public void itemRemoved(int index, Object value, List list) {
				if(index<=n0.intValue())
					update();
			}
			
			@Override
			public void itemAdded(int index, Object value, List list) {
				if(index<n0.intValue()) {
					update();
				}
				else if(index == n0.intValue()) {
					valueListener.valueUpdated(oldValue, value);
					oldValue = value;
				}
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
