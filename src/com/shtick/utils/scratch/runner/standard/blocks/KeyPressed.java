/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class KeyPressed extends AbstractOpcodeValue{
	private HashMap<ValueListener, KeyListener> listeners = new HashMap<>();

	/**
	 */
	public KeyPressed() {
		super(1, "keyPressed:");
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
		return runtime.isKeyPressed((String)arguments[0]);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		super.addValueListener(valueListener);
		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Object[] arguments = getArgumentState(valueListener);
				try {
					String s0 = (String)arguments[0];
					reportValue(valueListener, valueListener.getRuntime().isKeyPressed(s0));
				}
				catch(NullPointerException t) {
					System.err.println(arguments);
					System.err.println(arguments[0]);
					System.err.println(valueListener.getRuntime());
					throw t;
				}
			}
		};
		valueListener.getRuntime().addKeyListener(keyListener);
		listeners.put(valueListener, keyListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		super.removeValueListener(valueListener);
		valueListener.getRuntime().removeKeyListener(listeners.get(valueListener));
		listeners.remove(valueListener);
	}

}
