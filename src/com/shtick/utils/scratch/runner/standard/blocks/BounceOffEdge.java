/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.Rectangle;
import java.awt.geom.Area;

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
public class BounceOffEdge implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "bounceOffEdge";
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
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Sprite sprite = (Sprite)context.getContextObject();
		Area area = sprite.getSpriteShape();
		Rectangle rect = area.getBounds();
		rect.translate((int)sprite.getScratchX(), -(int)sprite.getScratchY());

		int stageWidth = runtime.getStageWidth();
		int stageHeight = runtime.getStageHeight();
		double direction = sprite.getDirection();
		boolean bounced = false;
		int xAdj = 0;
		int yAdj = 0;

		// Bounce X
		if(rect.x+rect.width>stageWidth/2) {
			if((direction>0)&&(direction<180)) {
				direction = 0 - direction;
				xAdj = stageWidth/2 - (rect.x+rect.width);
				bounced = true;
			}
		}
		else if(rect.x<-stageWidth/2) {
			if((direction<0)&&(direction>-180)) {
				direction = 0 - direction;
				xAdj = -stageWidth/2 - rect.x;
				bounced = true;
			}
		}
		
		// Bounce Y
		if(rect.y+rect.height>stageHeight/2) {
			// Bounce off bottom.
			if((direction>90)||(direction<-90)) {
				direction = 180 - direction;
				yAdj = -(stageHeight/2 - (rect.y+rect.height));
				bounced = true;
			}
		}
		else if(rect.y<-stageHeight/2) {
			// Bounce off top.
			if((direction>-90)&&(direction<90)) {
				direction = 180 - direction;
				yAdj = stageHeight/2 + rect.y;
				bounced = true;
			}
		}
		
		if(bounced) {
			double x = sprite.getScratchX()+xAdj;
			double y = sprite.getScratchY()+yAdj;
			sprite.setDirection(direction);
			sprite.gotoXY(x, y);
		}
		return null;
	}

}
