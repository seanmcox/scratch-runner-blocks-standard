/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.LinkedList;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;

/**
 * @author sean.cox
 *
 */
public class WhenGreenFlag implements OpcodeHat {
	private LinkedList<ScriptTuple> listeners = new LinkedList<>();
	private Timer timerOpcode;

	/**
	 * 
	 * @param timerOpcode
	 */
	public WhenGreenFlag(Timer timerOpcode) {
		this.timerOpcode = timerOpcode;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "whenGreenFlag";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
		timerOpcode.tare();
		synchronized(listeners) {
			for(ScriptTuple tuple:listeners)
				runtime.startScript(tuple);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void registerListeningScript(ScriptTuple script, Object[] params) {
		synchronized(listeners) {
			listeners.add(script);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void unregisterListeningScript(ScriptTuple script, Object[] params) {
		synchronized(listeners) {
			listeners.remove(script);
		}
	}

}
