/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.LinkedList;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.StandardBlocksExtensions;

/**
 * @author sean.cox
 *
 */
public class Timer implements OpcodeValue {
	private LinkedList<ValueListener> valueListeners = new LinkedList<>();
	

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "timer";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		return StandardBlocksExtensions.getElapsedTimeMillis()/1000.0;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		synchronized(valueListeners) {
			valueListeners.add(valueListener);
			if(valueListeners.size()==1) {
				new Thread(()->{
					Object oldValue = null;
					while(true) {
						synchronized(valueListeners) {
							if(valueListeners.size()==0)
								break;
							for(ValueListener listener:valueListeners)
								listener.valueUpdated(oldValue, StandardBlocksExtensions.getElapsedTimeMillis()/1000.0);
						}
						try {
							Thread.sleep(100);
						}
						catch(InterruptedException t) {}
					}
				}).start();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		synchronized(valueListeners) {
			valueListeners.remove(valueListener);
		}
	}

}
