/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.geom.Point2D;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeValue;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.SpriteListener;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * @author sean.cox
 *
 */
public class Heading implements OpcodeValue {
	private HashMap<ValueListener,SpriteListener> listeners = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "heading";
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
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Sprite sprite = (Sprite)context.getContextObject();
		return sprite.getDirection();
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		if(valueListener.getArguments().length!=0)
			throw new IllegalArgumentException("0 arguments expected for "+getOpcode()+" opcode");
		if(!(valueListener.getScriptContext().getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Sprite sprite = (Sprite)valueListener.getScriptContext().getContextObject();
		SpriteListener spriteListener = new SpriteListener() {
			@Override
			public void visibilityChanged(boolean newVisibility) {}
			
			@Override
			public void scaleChanged(double oldValue, double newValue) {}
			
			@Override
			public void rotationStyleChanged(String oldValue, String newValue) {}
			
			@Override
			public void positionChanged(Point2D oldPoint, Point2D newPoint) {}
			
			@Override
			public void headingChanged(double oldValue, double newValue) {
				valueListener.valueUpdated(oldValue, newValue);
			}
			
			@Override
			public void effectChanged(String effect, double oldValue, double newValue) {}
			
			@Override
			public void costumeChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {}
		};
		sprite.addSpriteListener(spriteListener);
		listeners.put(valueListener,spriteListener);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		SpriteListener spriteListener = listeners.get(valueListener);
		if(spriteListener == null)
			return;
		Sprite sprite = (Sprite)valueListener.getScriptContext().getContextObject();
		sprite.removeSpriteListener(spriteListener);
		listeners.remove(valueListener);
	}

}
