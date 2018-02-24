/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.HashMap;

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
public class Touching extends AbstractOpcodeValue {
	private HashMap<ValueListener,SpriteListener> listeners1 = new HashMap<>();
	private HashMap<ValueListener,SpriteListener> listeners2 = new HashMap<>();

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
		if(!sprite2.isVisible())
			return false;

		Area spriteShape = sprite.getSpriteShape();
		Area spriteShape2 = sprite2.getSpriteShape();

		synchronized(sprite.getSpriteLock()){
			spriteShape.transform(AffineTransform.getTranslateInstance(sprite.getScratchX(), -sprite.getScratchY()));
		}
		synchronized(sprite2.getSpriteLock()){
			spriteShape2.transform(AffineTransform.getTranslateInstance(sprite2.getScratchX(), -sprite2.getScratchY()));
		}
		spriteShape2.intersect(spriteShape);
		if(!spriteShape2.isEmpty())
			return true;
		for(Sprite clone:sprite2.getClones()) {
			spriteShape2 = clone.getSpriteShape();
			spriteShape2.intersect(spriteShape);
			if(!spriteShape2.isEmpty())
				return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#addValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void addValueListener(ValueListener valueListener) {
		super.addValueListener(valueListener);
		Object[] arguments = valueListener.getArguments();
		if(!(valueListener.getScriptContext().getContextObject() instanceof Sprite))
			throw new IllegalArgumentException("forward: opcode only valid in Sprite context.");
		final Sprite sprite1 = (Sprite)valueListener.getScriptContext().getContextObject();
		String arg0 = (String)arguments[0];
		Sprite sprite2 = valueListener.getRuntime().getSpriteByName(arg0);
		if(sprite2==null)
			throw new IllegalArgumentException("Sprite not found.");
		final Area[] spriteShapes = new Area[2];
		SpriteListener spriteListener1 = new SpriteListener() {
			private void update() {
				synchronized(sprite1) {
					Sprite sprite = sprite1;
					spriteShapes[0] = sprite.getSpriteShape();
					Area shape = spriteShapes[0];
					shape.transform(AffineTransform.getTranslateInstance(sprite.getScratchX(), -sprite.getScratchY()));
					shape = (Area)shape.clone();
					shape.intersect(spriteShapes[1]);
					Touching.this.reportValue(valueListener, !shape.isEmpty());
				}
			}
			
			@Override
			public void visibilityChanged(boolean newVisibility) {
				update();
			}
			
			@Override
			public void scaleChanged(double oldValue, double newValue) {
				update();
			}
			
			@Override
			public void rotationStyleChanged(String oldValue, String newValue) {
				update();
			}
			
			@Override
			public void positionChanged(Point2D oldPoint, Point2D newPoint) {
				update();
			}
			
			@Override
			public void headingChanged(double oldValue, double newValue) {
				update();
			}
			
			@Override
			public void effectChanged(String effect, double oldValue, double newValue) {}
			
			@Override
			public void costumeChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {
				update();
			}
		};
		spriteShapes[0] = sprite1.getSpriteShape();
		SpriteListener spriteListener2 = new SpriteListener() {
			private void update() {
				synchronized(sprite1) {
					Sprite sprite = sprite2;
					spriteShapes[1] = sprite.getSpriteShape();
					Area shape = spriteShapes[1];
					shape.transform(AffineTransform.getTranslateInstance(sprite.getScratchX(), -sprite.getScratchY()));
					shape = (Area)shape.clone();
					shape.intersect(spriteShapes[0]);
					Touching.this.reportValue(valueListener, !shape.isEmpty());
				}
			}
			
			@Override
			public void visibilityChanged(boolean newVisibility) {
				update();
			}
			
			@Override
			public void scaleChanged(double oldValue, double newValue) {
				update();
			}
			
			@Override
			public void rotationStyleChanged(String oldValue, String newValue) {
				update();
			}
			
			@Override
			public void positionChanged(Point2D oldPoint, Point2D newPoint) {
				update();
			}
			
			@Override
			public void headingChanged(double oldValue, double newValue) {
				update();
			}
			
			@Override
			public void effectChanged(String effect, double oldValue, double newValue) {}
			
			@Override
			public void costumeChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {
				update();
			}
		};
		spriteShapes[1] = sprite2.getSpriteShape();
		sprite1.addSpriteListener(spriteListener1);
		sprite2.addSpriteListener(spriteListener2);
		listeners1.put(valueListener,spriteListener1);
		listeners2.put(valueListener,spriteListener2);
		// TODO Handle the case when the second sprite is identified with a BlockTuple
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeValue#removeValueListener(com.shtick.utils.scratch.runner.core.ValueListener)
	 */
	@Override
	public void removeValueListener(ValueListener valueListener) {
		super.removeValueListener(valueListener);
		SpriteListener spriteListener1 = listeners1.get(valueListener);
		if(spriteListener1 != null) {
			Sprite sprite = (Sprite)valueListener.getScriptContext().getContextObject();
			sprite.removeSpriteListener(spriteListener1);
		}
		SpriteListener spriteListener2 = listeners2.get(valueListener);
		if(spriteListener2 != null) {
			String arg0 = (String)valueListener.getArguments()[0];
			Sprite sprite = valueListener.getRuntime().getSpriteByName(arg0);
			if(sprite!=null)
				sprite.removeSpriteListener(spriteListener2);
		}
		listeners1.remove(valueListener);
		listeners2.remove(valueListener);
	}
}
