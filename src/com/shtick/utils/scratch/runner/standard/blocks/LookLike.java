/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

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
public class LookLike implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "lookLike:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.OBJECT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Sprite sprite = (Sprite)context.getContextObject();
		Object a0 = arguments[0];
		if(a0 instanceof Boolean)
			a0 = OpcodeUtils.getNumericValue(a0);
		if(a0 instanceof Number) {
			if(!sprite.setCurrentCostumeIndex(((Number)a0).intValue()-1))
				sprite.setCurrentCostumeIndex(1);
		}
		if(!sprite.setCostumeByName(OpcodeUtils.getStringValue(a0))) {
			Number n0=OpcodeUtils.getNumericValue(a0);
			if(!sprite.setCurrentCostumeIndex(((Number)n0).intValue()-1))
				System.err.println("WARNING: Costume name, "+a0+", not found for sprite, "+context.getObjName());
		}
		return null;
	}

}
