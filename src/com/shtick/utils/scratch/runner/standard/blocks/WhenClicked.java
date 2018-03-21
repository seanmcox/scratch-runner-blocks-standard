/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.LinkedList;

import com.shtick.utils.scratch.runner.core.OpcodeHat;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.RenderableChild;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.core.elements.Sprite;

/**
 * I don't much like it, but this opcode will just be a placeholder while the real work of responding to this event is done in the SpriteImplementation
 * 
 * @author sean.cox
 *
 */
public class WhenClicked implements OpcodeHat {
	private LinkedList<ScriptTuple> listeners = new LinkedList<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "whenClicked";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
		runtime.addStageMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				LinkedList<ScriptTuple> clicked = new LinkedList<>();
				synchronized(listeners) {
					for(ScriptTuple script:listeners) {
						Sprite sprite = (Sprite)script.getContext();
						Area spriteShape = sprite.getSpriteShape();
						synchronized(sprite.getSpriteLock()){
							spriteShape.transform(AffineTransform.getTranslateInstance(sprite.getScratchX(), -sprite.getScratchY()));
						}
						if(spriteShape.contains(new Point(e.getX(),-e.getY())))
							clicked.add(script);
					}
				}
				if(clicked.size()>0) {
					RenderableChild[] children = runtime.getAllRenderableChildren();
					RenderableChild topTouched=null;
					for(RenderableChild child:children) {
						if(!(child instanceof Sprite))
							continue;
						for(ScriptTuple script:clicked) {
							if(script.getContext() == child) {
								topTouched = child;
								break;
							}
						}
					}
					if(topTouched==null)
						return;
					for(ScriptTuple script:clicked) {
						if(script.getContext() == topTouched)
							runtime.startScript(script);
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void registerListeningScript(ScriptTuple script, Object[] params) {
		if(!(script.getContext() instanceof Sprite))
			throw new IllegalArgumentException("Script context must be a sprite.");
		synchronized(listeners) {
			listeners.add(script);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, java.lang.Object[])
	 */
	@Override
	public void unregisterListeningScript(ScriptTuple script, Object[] params) {
		synchronized(listeners) {
			listeners.remove(script);
		}
	}
}
