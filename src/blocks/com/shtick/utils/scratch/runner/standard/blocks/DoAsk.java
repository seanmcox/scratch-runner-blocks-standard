/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.ui.DoAskUI;

/**
 * @author sean.cox
 *
 */
public class DoAsk implements OpcodeAction {
	private Answer answerOpcode;

	/**
	 * 
	 * @param answerOpcode
	 */
	public DoAsk(Answer answerOpcode) {
		this.answerOpcode = answerOpcode;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doAsk";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		String s0 = (String)arguments[0];

		final boolean[] isDone = new boolean[] {false};
		final DoAskUI textField = new DoAskUI(s0);
		int height = textField.getPreferredSize().height;
		int hMargin=15;
		int vMargin=8;
		runtime.addComponent(textField, -runtime.getStageWidth()/2+hMargin, runtime.getStageHeight()/2-height-vMargin, runtime.getStageWidth()-hMargin*2, height);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = textField.getText();
				answerOpcode.setAnswer(text);
				runtime.removeComponent(textField);
				isDone[0] = true;
			}
		});
		return new OpcodeSubaction() {
			
			@Override
			public boolean shouldYield() {
				if(isDone[0])
					return false;
				return true;
			}
			
			@Override
			public Type getType() {
				return Type.YIELD_CHECK;
			}
			
			@Override
			public ScriptTuple getSubscript() {
				return null;
			}

			@Override
			public boolean isSubscriptAtomic() {
				return false;
			}
		};
	}

}
