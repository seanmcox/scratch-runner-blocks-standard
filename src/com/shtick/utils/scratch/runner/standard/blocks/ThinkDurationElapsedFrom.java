/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.Image;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;
import com.shtick.utils.scratch.runner.standard.StandardBlocksExtensions;

/**
 * @author sean.cox
 *
 */
public class ThinkDurationElapsedFrom implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "think:duration:elapsed:from:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING,DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		String s0 = (String)arguments[0];
		Number n1 = (Number)arguments[1];
		Sprite sprite = (Sprite)context.getContextObject();

		Image bubbleImage = StandardBlocksExtensions.createThoughtBubbleImage(s0);
		runtime.setSpriteBubbleImage(sprite, bubbleImage);
		try {
			Thread.sleep((long)(n1.doubleValue()*1000));
		}
		catch(InterruptedException t) {}
		runtime.setSpriteBubbleImage(null,null);
	}

}
