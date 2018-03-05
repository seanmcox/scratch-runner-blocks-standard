/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.geom.Point2D;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class DistanceTo extends AbstractOpcodeValue {
	/**
	 */
	public DistanceTo() {
		super(1, "distanceTo:");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		String s0 = (String)arguments[0];
		Sprite sprite = (Sprite)context.getContextObject();
		double x,y;
		if(s0.equals("_mouse_")) {
			Point2D.Double mousePosition = runtime.getMouseStagePosition();
			return mousePosition.distance(sprite.getScratchX(), sprite.getScratchY());
		}
		Sprite sprite2 = runtime.getSpriteByName(s0);
		if(sprite2==null)
			throw new IllegalArgumentException("Sprite not found.");
		x = sprite2.getScratchX();
		y = sprite2.getScratchY();
		return new Point2D.Double(x,y).distance(sprite.getScratchX(), sprite.getScratchY());
	}
}
