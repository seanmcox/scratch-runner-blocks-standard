package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.List;
import com.shtick.utils.scratch.runner.standard.blocks.SetLineOfListTo;
import com.shtick.utils.scratch.runner.standard.util.AllBadList;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class SetLineOfListToTest {

	@Test
	void testOpcode() {
		SetLineOfListTo op = new SetLineOfListTo();
		assertEquals("setLine:ofList:to:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		SetLineOfListTo op = new SetLineOfListTo();
		assertArrayEquals(new DataType[] {DataType.OBJECT,DataType.STRING,DataType.OBJECT}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		SetLineOfListTo op = new SetLineOfListTo();

		{ // 0
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
			Object retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {0,"A","l"});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
		}

		{ // 1
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,"A",1});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals(1,sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
		}

		{ // 2
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {2,"A","owl"});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("owl",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
		}

		{ // last
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"last","A","l"});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("l",sprite.getContextListByName("A").getItem(3));
		}

		{ // 4
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
			Object retval = op.execute(new AllBadRuntime(), new StackTraceRunner(), sprite, new Object[] {4,"A","l"});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			assertEquals("A",sprite.getContextListByName("A").getItem(1));
			assertEquals("B",sprite.getContextListByName("A").getItem(2));
			assertEquals("C",sprite.getContextListByName("A").getItem(3));
		}

		{ // random
			AllBadSprite sprite = new ListySprite();
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"random","A","rand"});
			assertNull(retval);
			assertEquals(3,sprite.getContextListByName("A").getItemCount());
			Arrays.asList(sprite.getContextListByName("A").getContents()).contains("rand");
		}

		try{ // Bad value
			AllBadSprite sprite = new ListySprite();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {null,"A",null});
			fail("Exception expected.");
		}
		catch(Throwable t) {
			// Expected result.
		}

		try{ // Bad value
			AllBadSprite sprite = new ListySprite();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,1,1});
			fail("Exception expected.");
		}
		catch(Throwable t) {
			// Expected result.
		}

		try{ // Bad value
			AllBadSprite sprite = new ListySprite();
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {1,1,null});
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
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadList#addItem(java.lang.Object, int)
		 */
		@Override
		public void addItem(Object item, int index) {
			items.add(index-1, item);;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadList#setItem(java.lang.Object, int)
		 */
		@Override
		public void setItem(Object item, int index) {
			items.set(index-1, item);
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
