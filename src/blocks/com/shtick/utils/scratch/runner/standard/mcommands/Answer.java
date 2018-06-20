/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.mcommands;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.StageMonitorCommand;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author sean.cox
 *
 */
public class Answer implements StageMonitorCommand {
	/**
	 * The command implemented by this StageMonitorCommand.
	 */
	public static final String COMMAND = "answer";
	
	private com.shtick.utils.scratch.runner.standard.blocks.Answer answerOpcode;

	/**
	 * 
	 * @param answerOpcode
	 */
	public Answer(com.shtick.utils.scratch.runner.standard.blocks.Answer answerOpcode) {
		this.answerOpcode = answerOpcode;
	}

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
		return answerOpcode.getAnswer();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		answerOpcode.addAnswerListener(valueListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.StageMonitorCommand#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		answerOpcode.removeAnswerListener(valueListener);
	}
}
