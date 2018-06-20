package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.standard.blocks.DeleteLineOfList;
import com.shtick.utils.scratch.runner.standard.blocks.GetLineOfList;
import com.shtick.utils.scratch.runner.standard.util.AllBadList;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class GetLineOfListTest {

	@Test
	void testOpcode() {
		GetLineOfList op = new GetLineOfList();
		assertEquals("getLine:ofList:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		GetLineOfList op = new GetLineOfList();
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		GetLineOfList op = new GetLineOfList();
		AllBadSprite sprite = new ListySprite();

		{ // List A
			Object retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {0,"A"});
			assertEquals("",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"A"});
			assertEquals("A",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,"A"});
			assertEquals("B",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {3,"A"});
			assertEquals("C",retval);
			retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {4,"A"});
			assertEquals("",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"2","A"});
			assertEquals("B",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"last","A"});
			assertEquals("C",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"random","A"});
			assertTrue(Arrays.asList("A","B","C").contains(retval));
		}

		{ // List Alpha
			Object retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {0,"Alpha"});
			assertEquals("",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"Alpha"});
			assertEquals("Alpha",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,"Alpha"});
			assertEquals("Beta",retval);
			retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {3,"Alpha"});
			assertEquals("Gamma",retval);
			retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {4,"Alpha"});
			assertEquals("",retval);
		}

		try{ // Bad value
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"1",1});
			fail("Exception expected.");
		}
		catch(Throwable t) {
			// Expected result.
		}

		try{ // Bad value
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null,"A"});
			fail("Exception expected.");
		}
		catch(Throwable t) {
			// Expected result.
		}
	}
	
	private class ListySprite extends AllBadSprite{
		
		private TestList listA = new TestList("A","B","C");
		private TestList listAlpha = new TestList("Alpha","Beta","Gamma");
		
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextListByName(java.lang.String)
		 */
		@Override
		public List getContextListByName(String name) {
			if("A".equals(name))
				return listA;
			if("Alpha".equals(name))
				return listAlpha;
			return null;
		}
	}
	
	private class TestList extends AllBadList {
		private java.util.List<Object> items;

		/**
		 * 
		 * @param items
		 */
		public TestList(Object ... items) {
			this.items = new ArrayList<>(Arrays.asList(items));
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getContents()
		 */
		@Override
		public Object[] getContents() {
			return items.toArray();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItem(int)
		 */
		@Override
		public Object getItem(int index) {
			return items.get(index-1);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItemCount()
		 */
		@Override
		public int getItemCount() {
			return items.size();
		}
	}
	
	private class StackTraceRunner extends AllBadRunner{
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner#getStackTrace()
		 */
		@Override
		public String getStackTrace() {
			return "";
		}
	}
}
