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

/**
 * @author sean.cox
 *
 */
public class Answer implements OpcodeValue {
	private final LinkedList<ValueListener> ANSWER_LISTENERS = new LinkedList<>();
	private String answer;

	/**
	 * 
	 */
	public Answer() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "answer";
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
		return answer;
	}
	
	/**
	 * 
	 * @param valueListener
	 */
	public void addAnswerListener(ValueListener valueListener) {
		synchronized(ANSWER_LISTENERS) {
			ANSWER_LISTENERS.add(valueListener);
		}
	}
	
	/**
	 * 
	 * @param valueListener
	 */
	public void removeAnswerListener(ValueListener valueListener) {
		synchronized(ANSWER_LISTENERS) {
			ANSWER_LISTENERS.remove(valueListener);
		}
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		if((answer==this.answer)||((answer!=null)&&(!answer.equals(this.answer)))) {
			String oldAnswer = this.answer;
			this.answer = answer;
			for(ValueListener listener:ANSWER_LISTENERS)
				listener.valueUpdated(oldAnswer, answer);
		}
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
}
