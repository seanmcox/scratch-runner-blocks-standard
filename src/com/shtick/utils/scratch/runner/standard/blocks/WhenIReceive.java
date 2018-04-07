/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;

/**
 * @author sean.cox
 *
 */
public class WhenIReceive implements OpcodeHat {
	private TreeMap<String,java.util.List<ScriptTuple>> listeners = new TreeMap<>();
	private ScratchRuntime runtime;

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "whenIReceive";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
		this.runtime = runtime;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void registerListeningScript(ScriptTuple script, Object[] params) {
		synchronized(listeners) {
			if(!listeners.containsKey(params[0]))
				listeners.put((String)params[0], new LinkedList<>());
			listeners.get(params[0]).add(script);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void unregisterListeningScript(ScriptTuple script, Object[] params) {
		synchronized(listeners) {
			if(!listeners.containsKey(params[0]))
				return;
			java.util.List<ScriptTuple> scripts = listeners.get(params[0]);
			scripts.remove(script);
			if(scripts.size()==0)
				listeners.remove(params[0]);
		}
	}

	/**
	 * 
	 * @param message
	 * @return A Set of ScriptTupleRunners. Modification of the set has no side-effects.
	 */
	public Set<ScriptTupleRunner> broadcast(String message) {
		System.out.println("Broadcast: "+message);
		java.util.List<ScriptTuple> messageListeners;
		synchronized(listeners) {
			messageListeners = listeners.get(message);
			if(messageListeners == null)
				return new HashSet<>(1);
			messageListeners = new LinkedList<>(messageListeners);
		}
		HashSet<ScriptTupleRunner> runners = new HashSet<>(messageListeners.size());
		for(ScriptTuple tuple:messageListeners)
			runners.add(runtime.startScript(tuple));
		return runners;
	}
}
