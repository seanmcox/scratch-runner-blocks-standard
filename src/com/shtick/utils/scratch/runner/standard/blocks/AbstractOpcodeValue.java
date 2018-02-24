/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.Opcode;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public abstract class AbstractOpcodeValue implements OpcodeValue{
	private static String[] ORDINALS = new String[] {"first","second","third","fourth","fifth"};
	private int argumentCount;
	private String opcode;
	private HashMap<ValueListener,ValueListener[]> sublistenersByValueListener = new HashMap<>();
	private HashMap<ValueListener,Object[]> argumentsByValueListener = new HashMap<>();
	
	/**
	 * Each results array represents the result of calculating this OpcodeValue in the context of the ValueListener.
	 * This array is shared by other PassThroughValueListeners that are supporting the tracking of changes to this OpcodeValue in the context of the valueListener.
	 * 
	 * results[0] is the old value.
	 * results[1] is the new value.
	 */
	private HashMap<ValueListener,Object[]> resultsByValueListener = new HashMap<>();
	
	/**
	 * @param argumentCount
	 * @param opcode
	 */
	public AbstractOpcodeValue(int argumentCount, String opcode) {
		super();
		this.argumentCount = argumentCount;
		this.opcode = opcode;
	}

	/**
	 * @return the argumentCount
	 */
	public int getArgumentCount() {
		return argumentCount;
	}

	@Override
	public String getOpcode() {
		return opcode;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		if(argumentCount==0)
			return;
		Object[] arguments = valueListener.getArguments();
		if(arguments==null)
			throw new RuntimeException("ValueListener added with arguments required but null arguments.");
		Object[] results = new Object[2];
		synchronized(results) {
			argumentsByValueListener.put(valueListener, arguments);
			resultsByValueListener.put(valueListener, results);
			ValueListener[] sublisteners = new ValueListener[arguments.length];
			for(int i=0;i<arguments.length;i++) {
				if(!(arguments[i] instanceof BlockTuple))
					continue;
				BlockTuple blockTuple = (BlockTuple)arguments[i];
				Opcode opcode = valueListener.getScriptRunner().getOpcode(blockTuple);
				if((opcode==null)||(!(opcode instanceof OpcodeValue)))
					throw new IllegalArgumentException("OpcodeValue contained BlockTuple identifying non-OpcodeValue, or unrecognized Opcode: "+opcode);
				PassThroughValueListener sublistener = new PassThroughValueListener(valueListener, blockTuple, arguments, i);
				sublisteners[i] = sublistener;
				((OpcodeValue)opcode).addValueListener(sublistener);
				if(arguments[i] instanceof BlockTuple)
					arguments[i] = ((OpcodeValue)opcode).execute(valueListener.getRuntime(), valueListener.getScriptRunner(), valueListener.getScriptContext(), blockTuple.getArguments());
			}
			sublistenersByValueListener.put(valueListener, sublisteners);
			results[1] = execute(valueListener.getRuntime(), valueListener.getScriptRunner(), valueListener.getScriptContext(),arguments);
		}
		valueListener.valueUpdated(null, results[1]);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		Object[] arguments = valueListener.getArguments();
		ValueListener[] sublisteners = sublistenersByValueListener.get(valueListener);
		sublistenersByValueListener.remove(valueListener);
		argumentsByValueListener.remove(valueListener);
		resultsByValueListener.remove(valueListener);
		if(sublisteners==null)
			return;
		
		for(int i=0;i<arguments.length;i++) {
			if(!(arguments[i] instanceof BlockTuple))
				continue;
			BlockTuple blockTuple = (BlockTuple)arguments[i];
			Opcode opcode = valueListener.getScriptRunner().getOpcode(blockTuple);
			((OpcodeValue)opcode).removeValueListener(sublisteners[i]);
		}
	}
	
	/**
	 * 
	 * @param valueListener
	 * @return The array of evaluated arguments representing the current state of all arguments relevant to the given valueListener, or null if the valueListener isn't registered.
	 */
	public Object[] getArgumentState(ValueListener valueListener) {
		return argumentsByValueListener.get(valueListener);
	}
	
	/**
	 * 
	 * @param valueListener
	 * @param value not null
	 */
	public void reportValue(ValueListener valueListener, Object value) {
		/*
		 * Represents the result of calculating this OpcodeValue in the context of the ValueListener.
		 * This array is shared by other PassThroughValueListeners that are supporting the tracking of changes to this OpcodeValue in the context of the valueListener.
		 * 
		 * results[0] is the old value.
		 * results[1] is the new value.
		 */
		Object[] results = resultsByValueListener.get(valueListener);
		synchronized(results) {
			if(results == null)
				return;
			results[0] = results[1];
			results[1] = value;
			if(!results[0].equals(results[1]))
				valueListener.valueUpdated(results[0], results[1]);
		}
	}

	private class PassThroughValueListener implements ValueListener{
		private ValueListener valueListener;
		private BlockTuple blockTuple;
		/**
		 * Represents the current calculated values of the arguments supporting the context of the valueListener.
		 * This array is shared by other PassThroughValueListeners that are supporting the tracking of changes to this OpcodeValue in the context of the valueListener.
		 * 
		 */
		private Object[] arguments;
		/**
		 * The index of the argument in arguments that is the calculated value of the argument being tracked by this PassThroughValueListeners.
		 */
		private int index;

		/**
		 * @param valueListener
		 * @param blockTuple
		 * @param arguments Not the arguments of the blockTuple, but a set of arguments for the current opcode within the context of a BlockTuple which is the parent of blockTuple.
		 * @param index The index of the argument in arguments that is the calculated value of the argument being tracked by this PassThroughValueListeners.
		 */
		public PassThroughValueListener(ValueListener valueListener, BlockTuple blockTuple, Object[] arguments,
				int index) {
			super();
			this.valueListener = valueListener;
			this.blockTuple = blockTuple;
			this.arguments = arguments;
			this.index = index;
		}

		@Override
		public void valueUpdated(Object oldValue, Object newValue) {
			arguments[index] = newValue;
			if(oldValue==null)
				return;
			reportValue(valueListener,execute(valueListener.getRuntime(), valueListener.getScriptRunner(), valueListener.getScriptContext(), arguments));
		}
		
		@Override
		public ScriptTupleRunner getScriptRunner() {
			return valueListener.getScriptRunner();
		}
		
		@Override
		public ScriptContext getScriptContext() {
			return valueListener.getScriptContext();
		}
		
		@Override
		public ScratchRuntime getRuntime() {
			return valueListener.getRuntime();
		}
		
		@Override
		public Object[] getArguments() {
			return blockTuple.getArguments();
		}		
	}
}
