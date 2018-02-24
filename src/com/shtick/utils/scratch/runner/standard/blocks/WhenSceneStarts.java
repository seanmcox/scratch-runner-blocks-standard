/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.LinkedList;
import java.util.TreeMap;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;

/**
 * @author sean.cox
 *
 */
public class WhenSceneStarts implements OpcodeHat {
	private TreeMap<String,java.util.List<ScriptTuple>> listeners = new TreeMap<>();
	private static TreeMap<String,Object> notifiers = new TreeMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "whenSceneStarts";
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
		runtime.getCurrentStage().addStageListener(new StageListener() {
			
			@Override
			public void sceneChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {
				new Thread(()->{
					LinkedList<ScriptTupleRunner> runners = new LinkedList<>();
					synchronized(listeners) {
						java.util.List<ScriptTuple> scripts = listeners.get(newSceneName);
						if(scripts!=null) {
							for(ScriptTuple script:scripts)
								runners.add(runtime.startScript(script, false));
						}
					}
					for(ScriptTupleRunner runner:runners) {
						try {
							runner.join();
						}
						catch(InterruptedException t) {}
					}
					synchronized(notifiers) {
						Object notifier = notifiers.get(newSceneName);
						if(notifier!=null) {
							synchronized(notifier) {
								notifier.notifyAll();
							}
							notifiers.remove(newSceneName);
						}
					}
				}).start();
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

	/**
	 * 
	 * @param sceneName
	 * @return A lock object that will be notified when the scene has changed to the scene with the given sceneName, and all events initialized in response have completed.
	 *         This lock object is only valid until the next time it is notified.
	 */
	public static Object getSceneEventNotifier(String sceneName) {
		synchronized(notifiers) {
			if(notifiers.get(sceneName)==null) {
				notifiers.put(sceneName, new Object());
			}
			return notifiers.get(sceneName);
		}
	}
}
