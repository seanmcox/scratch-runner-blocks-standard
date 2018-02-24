/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.HashMap;

import com.shtick.utils.scratch.runner.core.OpcodeUtils;
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
public class DistanceTo extends AbstractOpcodeValue {
	private HashMap<ValueListener,SpriteListener> listeners1 = new HashMap<>();
	private HashMap<ValueListener,Object> listeners2 = new HashMap<>();

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
		String s0 = OpcodeUtils.getStringValue(arguments[0]);
		final Sprite sprite2;
		if(s0.equals("_mouse_")) {
			sprite2 = valueListener.getRuntime().getSpriteByName(s0);
			if(sprite2==null)
				throw new IllegalArgumentException("Sprite not found.");
		}
		else {
			sprite2 = null;
		}
		final Point2D[] points = new Point2D[2];
		SpriteListener spriteListener1 = new SpriteListener() {
			private void update() {
				synchronized(sprite1) {
					Sprite sprite = sprite1;
					points[0] = new Point2D.Double(sprite.getScratchX(), sprite.getScratchY());
					DistanceTo.this.reportValue(valueListener,points[0].distance(points[1]));
				}
			}
			
			@Override
			public void visibilityChanged(boolean newVisibility) {}
			
			@Override
			public void scaleChanged(double oldValue, double newValue) {}
			
			@Override
			public void rotationStyleChanged(String oldValue, String newValue) {}
			
			@Override
			public void positionChanged(Point2D oldPoint, Point2D newPoint) {
				update();
			}
			
			@Override
			public void headingChanged(double oldValue, double newValue) {}
			
			@Override
			public void effectChanged(String effect, double oldValue, double newValue) {}
			
			@Override
			public void costumeChanged(int oldSceneIndex, String oldSceneName, int newSceneIndex, String newSceneName) {}
		};
		points[0] = new Point2D.Double(sprite1.getScratchX(), sprite1.getScratchY());
		Object listener2;
		if(sprite2!=null) {
			listener2 = new SpriteListener() {
				private void update() {
					synchronized(sprite1) {
						Sprite sprite = sprite2;
						points[1] = new Point2D.Double(sprite.getScratchX(), sprite.getScratchY());
						DistanceTo.this.reportValue(valueListener,points[0].distance(points[1]));
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
			points[1] = new Point2D.Double(sprite2.getScratchX(), sprite2.getScratchY());
		}
		else {
			listener2 = new MouseMotionListener() {
				private void update(MouseEvent e) {
					synchronized(sprite1) {
						points[1] = e.getPoint();
						DistanceTo.this.reportValue(valueListener,points[0].distance(points[1]));
					}
				}
				
				@Override
				public void mouseMoved(MouseEvent e) {
					update(e);
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					update(e);
				}
			};
			points[1] = valueListener.getRuntime().getMouseStagePosition();
		}
		sprite1.addSpriteListener(spriteListener1);
		if(sprite2!=null)
			sprite2.addSpriteListener((SpriteListener)listener2);
		else
			valueListener.getRuntime().addStageMouseMotionListener((MouseMotionListener)listener2);
		listeners1.put(valueListener,spriteListener1);
		listeners2.put(valueListener,listener2);
		// TODO Handle the case when the second sprite (or mouse) is identified with a BlockTuple
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
		Object listener2 = listeners2.get(valueListener);
		if(listener2 != null) {
			if(listener2 instanceof SpriteListener) {
				String s0 = OpcodeUtils.getStringValue(valueListener.getArguments()[0]);
				Sprite sprite = valueListener.getRuntime().getSpriteByName(s0);
				if(sprite!=null)
					sprite.removeSpriteListener((SpriteListener)listener2);
			}
			else {
				valueListener.getRuntime().removeStageMouseMotionListener((MouseMotionListener)listener2);
			}
		}
		listeners1.remove(valueListener);
		listeners2.remove(valueListener);
	}
}
