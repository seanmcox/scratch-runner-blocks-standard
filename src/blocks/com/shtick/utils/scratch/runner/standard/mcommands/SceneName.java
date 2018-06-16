/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.mcommands;

import java.util.HashSet;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class SceneName implements StageMonitorCommand {
	private HashSet<ValueListener> valueListeners = new HashSet<>();
	private StageListener stageListener = new StageListener() {
		
		@Override
		public void sceneChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {
			for(ValueListener valueListener:valueListeners) {
				valueListener.valueUpdated(oldSceneName, newSceneName);
			}
		}
	};
	
	/**
	 * The command implemented by this StageMonitorCommand.
	 */
	public static final String COMMAND = "sceneName";

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#getCommand()
	 */
	@Override
	public String getCommand() {
		return COMMAND;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, java.lang.String)
	 */
	@Override
	public String execute(ScratchRuntime runtime, ScriptContext context, String param) {
		return runtime.getCurrentStage().getCurrentCostume().getCostumeName();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		synchronized(valueListeners) {
			if(valueListeners.size()==0) {
				valueListener.getRuntime().getCurrentStage().addStageListener(stageListener);
			}
		}
		valueListeners.add(valueListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		valueListeners.remove(valueListener);
		synchronized(valueListeners) {
			if(valueListeners.size()==0) {
				valueListener.getRuntime().getCurrentStage().removeStageListener(stageListener);
			}
		}
	}
}
