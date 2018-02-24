/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Stage;

/**
 * @author sean.cox
 *
 */
public class BackgroundIndex implements OpcodeValue {
	HashMap<ValueListener, StageListener> listeners = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "backgroundIndex";
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
		Stage stage = runtime.getCurrentStage();
		if(stage==null)
			return 0;
		return stage.getContextPropertyValueByName("background#");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		StageListener stageListener = new StageListener() {
			
			@Override
			public void sceneChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {
				valueListener.valueUpdated(oldSceneIndex, newSceneIndex);
			}
		};
		valueListener.getRuntime().getCurrentStage().addStageListener(stageListener);
		listeners.put(valueListener,stageListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		valueListener.getRuntime().getCurrentStage().removeStageListener(listeners.get(valueListener));
		listeners.remove(valueListener);
	}

}
