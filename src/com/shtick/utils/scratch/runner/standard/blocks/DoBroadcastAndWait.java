/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import java.util.Iterator;
import java.util.Set;

import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.StandardBlocksExtensions;

/**
 * @author sean.cox
 *
 */
public class DoBroadcastAndWait implements OpcodeAction {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doBroadcastAndWait";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context, Object[] arguments) {
		final Set<ScriptTupleRunner> scriptTupleRunners = StandardBlocksExtensions.broadcast((String)arguments[0]);
		return new OpcodeSubaction() {
			@Override
			public boolean shouldYield() {
				Iterator<ScriptTupleRunner> iter = scriptTupleRunners.iterator();
				ScriptTupleRunner runner;
				while(iter.hasNext()) {
					runner = iter.next();
					if(runner.isStopped())
						iter.remove();
					else
						return true;
				}
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

			@Override
			public boolean isSubscriptAtomic() {
				return false;
			}
		};
	}

}
