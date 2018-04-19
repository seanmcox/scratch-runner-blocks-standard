/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashMap;
import java.util.Iterator;
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
	private HashMap<String,java.util.List<ScriptTupleRunner>> scriptTupleRunners = new HashMap<>();

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
				synchronized(listeners) {
					java.util.List<ScriptTuple> scripts = listeners.get(newSceneName);
					java.util.List<ScriptTupleRunner> runners = new LinkedList<>();
					if(scripts!=null) {
						for(ScriptTuple script:scripts)
							runners.add(runtime.startScript(script));
					}
					scriptTupleRunners.put(newSceneName, runners);
				}
			}
		});
	}
	
	/**
	 * 
	 * @param sceneName
	 * @return true if scripts associated with the given scene change are still running.
	 */
	public boolean areSceneChangeScriptsRunning(String sceneName) {
		java.util.List<ScriptTupleRunner> runners = scriptTupleRunners.get(sceneName);
		if(runners == null)
			return false;
		Iterator<ScriptTupleRunner> iter = runners.iterator();
		ScriptTupleRunner runner;
		while(iter.hasNext()) {
			runner = iter.next();
			if(runner.isStopped())
				iter.remove();
			else
				return true;
		}
		return false;
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
