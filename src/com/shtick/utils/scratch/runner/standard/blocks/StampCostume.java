/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.Graphics2D;
import java.awt.Image;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.Costume;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class StampCostume implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "stampCostume";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Sprite sprite = (Sprite)context.getContextObject();
		if(!sprite.isVisible())
			return;
		Costume costume = sprite.getCurrentCostume();
		Image image = costume.getImage();

				// Contrary to the usual Scratch mangling of standard practices,
		// centerX and centerY have the usual meaning of being values
		// relative to the upper-left corner of the image, with values
		// increasing left to right and top to bottom respectively.
		int centerX = costume.getRotationCenterX();
		int centerY = costume.getRotationCenterX();
		
		Graphics2D g2 = runtime.getPenLayerGraphics();
		g2.translate(sprite.getScratchX(), -sprite.getScratchY());
		g2.scale(sprite.getScale(), sprite.getScale());
		switch(sprite.getRotationStyle()) {
		case "normal":
			g2.rotate((sprite.getDirection()-90)*Math.PI/180);
			break;
		case "leftRight":
			if(sprite.getDirection()<0)
				g2.scale(-1.0, 1.0);
			break;
		default:
			break;
		}
		g2.drawImage(image, -centerX, -centerY, null);
	}

}
