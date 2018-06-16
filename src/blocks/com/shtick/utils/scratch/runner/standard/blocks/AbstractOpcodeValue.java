/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ValueListener;

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
}
