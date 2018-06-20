package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.standard.blocks.Answer;
import com.shtick.utils.scratch.runner.standard.blocks.DoAsk;
import com.shtick.utils.scratch.runner.standard.blocks.ui.DoAskUI;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;

class DoAskTest {

	@Test
	void testOpcode() {
		DoAsk op = new DoAsk(new Answer());
		assertEquals("doAsk",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DoAsk op = new DoAsk(new Answer());
		assertArrayEquals(new DataType[] {DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		AllBadSprite sprite = new AllBadSprite();
		AskableRuntime runtime = new AskableRuntime();
		Answer answer = new Answer();
		DoAsk op = new DoAsk(answer);

		assertNull(runtime.component);
		assertNull(answer.getAnswer());

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"Question"});
			assertNotNull(result);
			assertTrue(result instanceof OpcodeSubaction);
			assertEquals(OpcodeSubaction.Type.YIELD_CHECK,((OpcodeSubaction)result).getType());
			assertTrue(((OpcodeSubaction)result).shouldYield());
			assertNotNull(runtime.component);
			assertTrue(runtime.component instanceof DoAskUI);
			Component[] components = ((DoAskUI)runtime.component).getComponents();
			AbstractButton button = null;
			JTextField textField = null;
			for(Component component:components){
				System.out.println(component.getClass());
				if(component instanceof JTextField)
					textField = (JTextField)component;
				else if(component instanceof AbstractButton)
					button = (AbstractButton)component;
			}
			assertNotNull(button);
			assertNotNull(textField);
			textField.setText("test");
			button.doClick();
			assertEquals("test",answer.getAnswer());
			assertFalse(((OpcodeSubaction)result).shouldYield());
			assertNull(runtime.component);
		}

		try {
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {null});
			fail("Exception expected.");
		}
		catch(Throwable t) {}

		try {
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {1L});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
	}
	
	private class AskableRuntime extends AllBadRuntime{
		public Component component;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadRuntime#addComponent(java.awt.Component, int, int, int, int)
		 */
		@Override
		public void addComponent(Component component, int x, int y, int width, int height) {
			this.component = component;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadRuntime#removeComponent(java.awt.Component)
		 */
		@Override
		public void removeComponent(Component component) {
			if(component == this.component)
				this.component = null;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadRuntime#getStageWidth()
		 */
		@Override
		public int getStageWidth() {
			return 480;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadRuntime#getStageHeight()
		 */
		@Override
		public int getStageHeight() {
			return 360;
		}
	}
}
