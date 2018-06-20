package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.standard.blocks.AppendToList;
import com.shtick.utils.scratch.runner.standard.util.AllBadList;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class AppendToListTest {

	@Test
	void testOpcode() {
		AppendToList op = new AppendToList();
		assertEquals("append:toList:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		AppendToList op = new AppendToList();
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		ListAppendOKSprite sprite = new ListAppendOKSprite();
		AppendToList op = new AppendToList();

		{
			sprite.reset();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item","test"});
			List list = sprite.getContextListByName("test");
			assertEquals(1,list.getItemCount());
			assertEquals("item",list.getItem(1));
		}

		{
			sprite.reset();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item","bad"});
			List list = sprite.getContextListByName("test");
			assertEquals(0,list.getItemCount());
		}
		
		try {
			sprite.reset();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
		
		{
			sprite.reset();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item","test","extra"});
			List list = sprite.getContextListByName("test");
			assertEquals(1,list.getItemCount());
			assertEquals("item",list.getItem(1));
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}
	}

	@Test
	void testAppend() {
		ListAppendOKSprite sprite = new ListAppendOKSprite();
		AppendToList op = new AppendToList();

		{
			sprite.reset();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item1","test"});
			List list = sprite.getContextListByName("test");
			assertEquals(1,list.getItemCount());
			assertEquals("item1",list.getItem(1));

			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"item2","test"});
			assertEquals(2,list.getItemCount());
			assertEquals("item1",list.getItem(1));
			assertEquals("item2",list.getItem(2));
		}
	}
	
	private static class ListAppendOK extends AllBadList{
		private LinkedList<Object> internalList = new LinkedList<>();

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItem(int)
		 */
		@Override
		public Object getItem(int index) {
			return internalList.get(index-1);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#addItem(java.lang.Object)
		 */
		@Override
		public void addItem(Object item) {
			internalList.add(item);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadList#getItemCount()
		 */
		@Override
		public int getItemCount() {
			return internalList.size();
		}
		
	}
	
	public static class ListAppendOKSprite extends AllBadSprite{
		List list = new ListAppendOK();
		
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextListByName(java.lang.String)
		 */
		@Override
		public List getContextListByName(String name) {
			if("test".equals(name))
				return list;
			return null;
		}
		
		public void reset() {
			list = new ListAppendOK();
		}
	}
}
