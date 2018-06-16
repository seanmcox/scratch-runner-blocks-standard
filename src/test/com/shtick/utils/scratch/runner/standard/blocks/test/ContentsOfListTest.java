package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.standard.blocks.ContentsOfList;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class ContentsOfListTest {

	@Test
	void testOpcode() {
		ContentsOfList op = new ContentsOfList();
		assertEquals("contentsOfList:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ContentsOfList op = new ContentsOfList();
		assertArrayEquals(new DataType[] {DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new ListySprite();
		ContentsOfList op = new ContentsOfList();

		{ // List of 1-length strings
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"A"});
			assertTrue(retval instanceof String);
			assertEquals("ABC",retval);
		}

		{ // List of longer strings
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Alpha"});
			assertTrue(retval instanceof String);
			assertEquals("Alpha Beta Gamma",retval);
		}

		{ // Non-list
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"Nonexistent"});
			assertTrue(retval instanceof String);
			assertEquals("", retval);
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
		private Object[] items;

		/**
		 * 
		 * @param items
		 */
		public TestList(Object ... items) {
			this.items = items;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getContents()
		 */
		@Override
		public Object[] getContents() {
			return items;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItem(int)
		 */
		@Override
		public Object getItem(int index) {
			return items[index-1];
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItemCount()
		 */
		@Override
		public int getItemCount() {
			return items.length;
		}
		
	}
}
