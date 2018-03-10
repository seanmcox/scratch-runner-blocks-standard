/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class Touching extends AbstractOpcodeValue {
	/**
	 */
	public Touching() {
		super(1, "touching:");
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
			throw new IllegalArgumentException("forward: opcode only valid in Sprite context.");
		String s0 = (String)arguments[0];
		Sprite sprite = (Sprite)context.getContextObject();
		if(!sprite.isVisible())
			return false;
		Sprite sprite2 = runtime.getSpriteByName(s0);
		if(sprite2==null)
			throw new IllegalArgumentException("Sprite not found.");
		Area spriteShape = sprite.getSpriteShape();
		Area spriteShape2;
		if(sprite2.isVisible()) {
			spriteShape2 = sprite2.getSpriteShape();
	
			synchronized(sprite.getSpriteLock()){
				spriteShape.transform(AffineTransform.getTranslateInstance(sprite.getScratchX(), -sprite.getScratchY()));
			}
			synchronized(sprite2.getSpriteLock()){
				spriteShape2.transform(AffineTransform.getTranslateInstance(sprite2.getScratchX(), -sprite2.getScratchY()));
			}
			spriteShape2.intersect(spriteShape);
			if(!spriteShape2.isEmpty())
				return true;
		}
		for(Sprite clone:sprite2.getClones()) {
			if(!clone.isVisible())
				continue;
			spriteShape2 = clone.getSpriteShape();
			spriteShape2.intersect(spriteShape);
			if(!spriteShape2.isEmpty())
				return true;
		}

		return false;
	}
}
