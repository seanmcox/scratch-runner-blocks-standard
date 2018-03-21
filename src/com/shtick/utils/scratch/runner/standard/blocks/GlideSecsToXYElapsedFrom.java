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
public class GlideSecsToXYElapsedFrom implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "glideSecs:toX:y:elapsed:from:";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.NUMBER,DataType.NUMBER,DataType.NUMBER};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.ScriptTupleRunner, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Object[] arguments) {
		if(!(context.getContextObject() instanceof Sprite))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Number n0 = (Number)arguments[0];
		Number n1 = (Number)arguments[1];
		Number n2 = (Number)arguments[2];
		Sprite sprite = (Sprite)context.getContextObject();
		if(n0.doubleValue()<0)
			throw new IllegalArgumentException("Time in seconds must be positive.");
		Point2D startPoint;
		synchronized(sprite.getSpriteLock()) {
			startPoint = new Point2D.Double(sprite.getScratchX(), sprite.getScratchY());
		}
		long timePeriodMillis = (long)(n0.doubleValue()*1000);
		if(timePeriodMillis>0) {
			long startTimeMillis = System.currentTimeMillis();
			long targetTimeMillis = startTimeMillis+timePeriodMillis;
			long currentTimeMillis = startTimeMillis;
			double tFactor,x,y;
			while(currentTimeMillis<targetTimeMillis) {
				tFactor = Math.min(1.0, (currentTimeMillis - startTimeMillis)/(double)timePeriodMillis);
				x = startPoint.getX()+(n1.doubleValue()-startPoint.getX())*tFactor;
				y = startPoint.getY()+(n2.doubleValue()-startPoint.getY())*tFactor;
				sprite.gotoXY(x,y);
				try {
					Thread.sleep(30);
				}
				catch(InterruptedException t) {}
				currentTimeMillis = System.currentTimeMillis();
			}
		}
		sprite.gotoXY(n1.doubleValue(),n2.doubleValue());
		return null;
	}

}
