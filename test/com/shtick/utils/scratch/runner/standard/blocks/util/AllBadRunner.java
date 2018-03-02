/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks.util;

import java.util.List;

import com.shtick.utils.scratch.runner.core.Opcode;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.BlockTuple;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;

/**
 * @author Sean
 *
 */
public class AllBadRunner implements ScriptTupleRunner {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#flagStop()
	 */
	@Override
	public void flagStop() {
		throw new UnsupportedOperationException("Called flagStop when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#isStopFlagged()
	 */
	@Override
	public boolean isStopFlagged() {
		throw new UnsupportedOperationException("Called isStopFlagged when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#runBlockTuples(com.shtick.utils.scratch.runner.core.elements.ScriptContext, java.util.List)
	 */
	@Override
	public void runBlockTuples(ScriptContext context, List<BlockTuple> script) {
		throw new UnsupportedOperationException("Called runBlockTuples when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#getOpcode(com.shtick.utils.scratch.runner.core.elements.BlockTuple)
	 */
	@Override
	public Opcode getOpcode(BlockTuple blockTuple) {
		throw new UnsupportedOperationException("Called getOpcode when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#getCurrentOpcode()
	 */
	@Override
	public Opcode getCurrentOpcode() {
		throw new UnsupportedOperationException("Called getCurrentOpcode when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#isAtomic()
	 */
	@Override
	public boolean isAtomic() {
		throw new UnsupportedOperationException("Called isAtomic when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#join(long, int)
	 */
	@Override
	public void join(long millis, int nanos) throws InterruptedException {
		throw new UnsupportedOperationException("Called join when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.ScriptTupleRunner#join()
	 */
	@Override
	public void join() throws InterruptedException {
		throw new UnsupportedOperationException("Called join when not expected.");
	}

}
