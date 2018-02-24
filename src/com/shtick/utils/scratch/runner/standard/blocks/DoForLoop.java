/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.OpcodeAction;
import com.shtick.utils.scratch.runner.core.OpcodeControl;
import com.shtick.utils.scratch.runner.core.OpcodeUtils;
import com.shtick.utils.scratch.runner.core.ScratchRuntime;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.core.elements.control.BasicJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.FalseJumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.JumpBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TestBlockTuple;
import com.shtick.utils.scratch.runner.core.elements.control.TrueJumpBlockTuple;

/**
 * Not a normally available block. For more information see:
 * https://scratch.mit.edu/discuss/topic/73246/?page=1#post-626178
 * 
 * @author sean.cox
 *
 */
public class DoForLoop implements OpcodeControl {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "doForLoop";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public DataType[] getArgumentTypes() {
		return new DataType[] {DataType.STRING, DataType.NUMBER, DataType.SCRIPT};
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeControl#execute(java.lang.Object[])
	 */
	@Override
	public BlockTuple[] execute(Object[] arguments) {
		java.util.List<BlockTuple> subtuples = (java.util.List<BlockTuple>)arguments[2];
		BlockTuple[] retval = new BlockTuple[subtuples.size()+5];
		BlockTuple readVar = new BlockTuple() {
			@Override
			public Object[] toArray() {
				return null;
			}
			@Override
			public String getOpcode() {
				return ReadVariable.OPCODE;
			}
			@Override
			public Object[] getArguments() {
				return new Object[] {arguments[0]};
			}
		};
		BlockTuple setVar = new BlockTuple() {
			@Override
			public Object[] toArray() {
				return null;
			}
			@Override
			public String getOpcode() {
				return SetVarTo.OPCODE;
			}
			@Override
			public Object[] getArguments() {
				return new Object[] {arguments[0],1};
			}
		};
		BlockTuple test = new BlockTuple() {
			@Override
			public Object[] toArray() {
				return null;
			}
			@Override
			public String getOpcode() {
				return _GreaterThan.OPCODE;
			}
			@Override
			public Object[] getArguments() {
				return new Object[] {readVar,arguments[1]};
			}
		};
		BlockTuple incrementVar = new BlockTuple() {
			@Override
			public Object[] toArray() {
				return null;
			}
			@Override
			public String getOpcode() {
				return ChangeVarBy.OPCODE;
			}
			@Override
			public Object[] getArguments() {
				return new Object[] {arguments[0],1};
			}
		};
		retval[0] = setVar;
		retval[1] = test;
		retval[2] = new TrueJumpBlockTuple(retval.length);
		int i=3;
		for(BlockTuple subtuple:subtuples) {
			retval[i] = subtuple;
			i++;
		}
		retval[retval.length-2] = incrementVar;
		retval[retval.length-1] = new BasicJumpBlockTuple(1);
		return retval;
	}

//	/* (non-Javadoc)
//	 * @see com.shtick.utils.scratch.runner.core.OpcodeAction#execute(com.shtick.utils.scratch.runner.core.ScratchRuntime, com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.lang.Object[])
//	 */
//	@Override
//	public void execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context, Object[] arguments) {
//		if(arguments.length!=3)
//			throw new IllegalArgumentException("3 arguments expected for "+getOpcode()+" opcode");
//		if(!OpcodeUtils.isEvaluable(arguments[0]))
//			throw new IllegalArgumentException("The first argument for opcode, "+getOpcode()+", must be evaluable.");
//		if(!OpcodeUtils.isEvaluable(arguments[1]))
//			throw new IllegalArgumentException("The second argument for opcode, "+getOpcode()+", must be evaluable.");
//		if((!(arguments[2] instanceof java.util.List<?>))&&(arguments[2]!=null))
//			throw new IllegalArgumentException("List of BlockTuples expected as third argument for "+getOpcode()+", but "+arguments[0].getClass().getCanonicalName()+" found.");
//		String s0 = OpcodeUtils.getStringValue(arguments[0]);
//		Number n1 = OpcodeUtils.getNumericValue(arguments[1]);
//		java.util.List<BlockTuple> subtuples = (arguments[2]!=null)?(java.util.List<BlockTuple>)arguments[2]:null;
//		int count = (n1 instanceof Integer)?n1.intValue():(int)Math.ceil(n1.doubleValue());
//		for(int i = 1;i<=count;i++) {
//			context.setContextVariableValueByName(s0, i);
//			scriptRunner.runBlockTuples(context, subtuples);
//		}
//		return;
//	}
//
}
