/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.Color;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class SetPenHueTo implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "setPenHueTo:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Number n0 = OpcodeUtils.getNumericValue(arguments[0]);
		Sprite sprite = (Sprite)context.getContextObject();
		if(n0 == arguments[0]) {
			sprite.setPenHue(n0.intValue());
		}
		else {
			int color = n0.intValue();
			if((color & 0xFF000000)!=0) {
				// The alpha scale is a little odd for scratch.
				// See: https://wiki.scratch.mit.edu/wiki/Pen#Formula
				color = ((((color >>> 24)-1)*255/254)<<24)|(color & 0x00FFFFFF);
				sprite.setPenColor(new Color(color,true));
			}
			else {
				sprite.setPenColor(new Color(color,false));
			}
		}
		return null;
	}

}
