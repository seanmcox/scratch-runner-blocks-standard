/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.FalseJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;

/**
 * @author sean.cox
 *
 */
public class DoWaitUntil implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doWaitUntil";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.BOOLEAN};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(Object[] arguments) {
		BlockTuple[] retval = new BlockTuple[2];
		retval[0] = new TestBlockTuple(arguments[0]);
		retval[1] = new FalseJumpBlockTuple(0);
		return retval;
	}

//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context, Object[] arguments) {
//		if(arguments.length!=1)
//			throw new IllegalArgumentException("2 arguments expected for "+getOpcode()+" opcode");
//		if(!OpcodeUtils.isEvaluable(arguments[0]))
//			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
//		if(arguments[0] instanceof Boolean) {
//			if(!((Boolean)arguments[0]).booleanValue())
//				scriptRunner.flagStop();
//			return;
//		}
//		if(!(arguments[0] instanceof BlockTuple))
//			throw new IllegalArgumentException("BlockTuple or Boolean expected.");
//		Opcode opcode = scriptRunner.getOpcode((BlockTuple)arguments[0]);
//		final Object lock = new Object();
//		ValueListener listener = new ValueListener() {
//			
//			@Override
//			public void valueUpdated(Object oldValue, Object newValue) {
//				synchronized(lock) {
//					if(((Boolean)newValue).booleanValue())
//						lock.notifyAll();
//				}
//			}
//			
//			@Override
//			public ScriptTupleRunner getScriptRunner() {
//				return scriptRunner;
//			}
//			
//			@Override
//			public ScriptContext getScriptContext() {
//				return context;
//			}
//			
//			@Override
//			public ScratchRuntime getRuntime() {
//				return runtime;
//			}
//			
//			@Override
//			public Object[] getArguments() {
//				return ((BlockTuple)arguments[0]).getArguments();
//			}
//		};
//		synchronized(lock) {
//			((OpcodeValue)opcode).addValueListener(listener);
//			Object value=arguments[0];
//			if(!(value instanceof Boolean)) {
//				((OpcodeValue)opcode).removeValueListener(listener);
//				throw new IllegalArgumentException("BlockTuple or Boolean expected.");
//			}
//			if(!((Boolean)value).booleanValue()) {
//				try {
//					lock.wait();
//				}
//				catch(InterruptedException t) {}
//			}
//			((OpcodeValue)opcode).removeValueListener(listener);
//		}
//		return;
//	}

}
