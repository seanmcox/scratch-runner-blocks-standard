/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.geom.Point2D;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class PointTowards implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "pointTowards:";
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
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		String arg0 = (String)arguments[0];
		Sprite sprite = (Sprite)context.getContextObject();
		double direction;
		if(arg0.equals("_mouse_")) {
			Point2D.Double mousePosition = runtime.getMouseStagePosition();
			double x = mousePosition.getX()-sprite.getScratchX();
			double y = mousePosition.getY()-sprite.getScratchY();
			direction = Math.acos(y/Math.sqrt(x*x+y*y));
			if(x<0)
				direction = -direction;
		}
		else {
			Sprite destSprite = runtime.getSpriteByName(arg0);
			if(destSprite==null)
				throw new IllegalArgumentException("Sprite not found.");
			double x = destSprite.getScratchX()-sprite.getScratchX();
			double y = destSprite.getScratchY()-sprite.getScratchY();
			direction = Math.acos(y/Math.sqrt(x*x+y*y));
			if(x<0)
				direction = -direction;
		}
		direction*=180/Math.PI;
		sprite.setDirection(direction);
		return null;
	}

}
