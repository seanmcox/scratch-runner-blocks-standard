/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.StandardFeatureGenerator;

/**
 * @author sean.cox
 *
 */
public class WhenKeyPressed implements OpcodeHat {
	private TreeMap<String,java.util.List<ScriptTuple>> listeners = new TreeMap<>();
	private HashMap<ScriptTuple,ScriptTupleRunner> scriptTupleRunners = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "whenKeyPressed";
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
		runtime.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				String keyID = StandardFeatureGenerator.getKeyIdForEvent(e);
				if(keyID == null)
					return;
				java.util.List<ScriptTuple> l;
				synchronized(listeners) {
					l = listeners.get(keyID);
					if(l!=null) {
						for(ScriptTuple script:l) {
							if((!scriptTupleRunners.containsKey(script))||(scriptTupleRunners.get(script).isStopped())) {
								scriptTupleRunners.put(script, runtime.startScript(script));
							}
						}
					}
					l = listeners.get("any");
				}
				if(l!=null) {
					for(ScriptTuple script:l) {
						if((!scriptTupleRunners.containsKey(script))||(scriptTupleRunners.get(script).isStopped())) {
							scriptTupleRunners.put(script, runtime.startScript(script));
						}
					}
				}
			}
		});
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
}
