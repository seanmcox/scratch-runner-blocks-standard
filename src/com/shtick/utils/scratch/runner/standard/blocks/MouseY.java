/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class MouseY implements OpcodeValue {
	private HashMap<ValueListener, MouseMotionListener> listeners = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "mouseY";
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
		return runtime.getMouseStagePosition().getY();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		MouseMotionListener mouseMotionListener = new MouseMotionListener() {
			double old = valueListener.getRuntime().getMouseStagePosition().getY();
			
			@Override
			public void mouseMoved(MouseEvent e) {
				synchronized(this) {
					update(e.getY());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				synchronized(this) {
					update(e.getY());
				}
			}
			
			private void update(int newValue) {
				if(newValue==old)
					return;
				valueListener.valueUpdated(old, newValue);
				old = newValue;
			}
		};
		valueListener.getRuntime().addStageMouseMotionListener(mouseMotionListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		valueListener.getRuntime().removeStageMouseMotionListener(listeners.get(valueListener));
		listeners.remove(valueListener);
	}

}
