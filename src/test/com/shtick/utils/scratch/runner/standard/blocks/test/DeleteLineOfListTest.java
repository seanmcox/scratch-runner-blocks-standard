package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.standard.blocks.DeleteLineOfList;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class DeleteLineOfListTest {

	@Test
	void testOpcode() {
		DeleteLineOfList op = new DeleteLineOfList();
		assertEquals("deleteLine:ofList:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DeleteLineOfList op = new DeleteLineOfList();
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		DeleteLineOfList op = new DeleteLineOfList();

		{ // Delete all
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"all","A"});
			assertNull(retval);
			assertEquals(0,sprite.getContextListByName("A").getItemCount());
		}

		{ // Delete last
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"last","A"});
			assertNull(retval);
			assertEquals(2,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
		}

		{ // Delete 1
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"A"});
			assertNull(retval);
			assertEquals(2,sprite.getContextListByName("A").getItemCount());
			assertEquals("B",sprite.getContextListByName("A").getItem(1));
			assertEquals("C",sprite.getContextListByName("A").getItem(2));
		}

		{ // Delete "1"
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"1","A"});
			assertNull(retval);
			assertEquals(2,sprite.getContextListByName("A").getItemCount());
			assertEquals("B",sprite.getContextListByName("A").getItem(1));
			assertEquals("C",sprite.getContextListByName("A").getItem(2));
		}

		AllBadSprite sprite = new ListySprite();
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

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#deleteItem(int)
		 */
		@Override
		public void deleteItem(int index) {
			items.remove(index-1);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#deleteAll()
		 */
		@Override
		public void deleteAll() {
			items.clear();
		}
	}
}
