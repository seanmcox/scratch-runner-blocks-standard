package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.standard.blocks.Answer;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;
import com.shtick.utils.scratch.runner.standard.util.BasicAbstractValueListener;

class AnswerTest {

	@Test
	void testOpcode() {
		Answer op = new Answer();
		assertEquals("answer",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Answer op = new Answer();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		AllBadSprite sprite = new AllBadSprite();
		Answer op = new Answer();

		{
			Object result = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertNull(result);
		}
		
		op.setAnswer("test");

		{
			Object result = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertEquals("test",result);
		}
		
		op.setAnswer("h");

		{
			Object result = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {});
			assertEquals("h",result);
		}
	}

	@Test
	void testGetAnswer() {
		Answer op = new Answer();

		{
			assertNull(op.getAnswer());
		}
		
		op.setAnswer("test");

		{
			assertEquals("test",op.getAnswer());
		}
		
		op.setAnswer("h");

		{
			assertEquals("h",op.getAnswer());
		}
	}

	@Test
	void testListeners() {
		Answer op = new Answer();
		TrackingValueListener la = new TrackingValueListener();
		TrackingValueListener lb = new TrackingValueListener();
		
		assertNull(la.lastOld);
		assertNull(la.lastNew);
		assertNull(lb.lastOld);
		assertNull(lb.lastNew);
		
		op.addAnswerListener(la);

		assertNull(la.lastOld);
		assertNull(la.lastNew);
		assertNull(lb.lastOld);
		assertNull(lb.lastNew);
		
		op.setAnswer("test");

		assertNull(la.lastOld);
		assertEquals("test",la.lastNew);
		assertNull(lb.lastOld);
		assertNull(lb.lastNew);

		op.setAnswer("h");

		assertEquals("test",la.lastOld);
		assertEquals("h",la.lastNew);
		assertNull(lb.lastOld);
		assertNull(lb.lastNew);

		op.addAnswerListener(lb);

		assertEquals("test",la.lastOld);
		assertEquals("h",la.lastNew);
		assertNull(lb.lastOld);
		assertNull(lb.lastNew);

		op.setAnswer("ello");

		assertEquals("h",la.lastOld);
		assertEquals("ello",la.lastNew);
		assertEquals("h",lb.lastOld);
		assertEquals("ello",lb.lastNew);
		
		op.removeAnswerListener(la);

		assertEquals("h",la.lastOld);
		assertEquals("ello",la.lastNew);
		assertEquals("h",lb.lastOld);
		assertEquals("ello",lb.lastNew);

		op.setAnswer("black");

		assertEquals("h",la.lastOld);
		assertEquals("ello",la.lastNew);
		assertEquals("ello",lb.lastOld);
		assertEquals("black",lb.lastNew);
	}
	
	private class TrackingValueListener extends BasicAbstractValueListener{
		public Object lastOld;
		public Object lastNew;

		/**
		 * 
		 */
		public TrackingValueListener() {
			super(null, null);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.BasicAbstractValueListener#valueUpdated(java.lang.Object, java.lang.Object)
		 */
		@Override
		public void valueUpdated(Object oldValue, Object newValue) {
			lastOld = oldValue;
			lastNew = newValue;
		}
	}
}
