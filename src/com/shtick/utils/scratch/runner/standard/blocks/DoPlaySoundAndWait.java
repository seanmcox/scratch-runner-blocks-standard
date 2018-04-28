/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.SoundMonitor;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;

/**
 * @author sean.cox
 *
 */
public class DoPlaySoundAndWait implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doPlaySoundAndWait";
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
		SoundMonitor soundMonitor = context.playSoundByName((String)arguments[0]);
		if(soundMonitor==null) {
			Number n0=OpcodeUtils.getNumericValue(arguments[0]);
			soundMonitor = context.playSoundByIndex(((Number)n0).intValue()-1);
		}
		if(soundMonitor==null) {
			System.err.println("WARNING: \""+getOpcode()+"\": Sound did not play (and wait) as expected: "+arguments[0]+" context="+context.getObjName());
			return null;
		}
		final SoundMonitor finalSoundMonitor = soundMonitor;
		return new OpcodeSubaction() {
			
			@Override
			public boolean shouldYield() {
				return !finalSoundMonitor.isDone();
			}
			
			@Override
			public boolean isSubscriptAtomic() {
				return false;
			}
			
			@Override
			public Type getType() {
				return Type.YIELD_CHECK;
			}
			
			@Override
			public ScriptTuple getSubscript() {
				return null;
			}
		};
	}

}
