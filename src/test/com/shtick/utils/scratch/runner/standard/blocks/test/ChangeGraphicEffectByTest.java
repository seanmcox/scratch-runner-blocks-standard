package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.ChangeGraphicEffectBy;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;
import com.shtick.utils.scratch.runner.standard.util.AllBadStage;

class ChangeGraphicEffectByTest {

	@Test
	void testOpcode() {
		ChangeGraphicEffectBy op = new ChangeGraphicEffectBy();
		assertEquals("changeGraphicEffect:by:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ChangeGraphicEffectBy op = new ChangeGraphicEffectBy();
		assertArrayEquals(new DataType[] {DataType.STRING,DataType.NUMBER}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		AllBadRuntime runtime = new AllBadRuntime();
		ChangeGraphicEffectBySprite sprite = new ChangeGraphicEffectBySprite();
		
		ChangeGraphicEffectBy op = new ChangeGraphicEffectBy();
		assertEqualWithinMargin(50, sprite.fx, 0.0001);

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"fx",1});
			assertNull(result);
			assertEqualWithinMargin(51, sprite.fx, 0.0001);
		}

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"fx",-1});
			assertNull(result);
			assertEqualWithinMargin(50, sprite.fx, 0.0001);
		}
		
		try{
			ScriptContext scriptContext = new AllBadStage();
			Object result = op.execute(runtime, new AllBadRunner(), scriptContext, new Object[] {});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"fx"});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {"fx", null});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
		
		try{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {null, 1});
			fail("Exception expected.");
		}
		catch(Throwable t) {}
	}

	private static void assertEqualWithinMargin(double expected, double actual, double margin) {
		assertTrue(actual>(expected-margin),"Actual value, "+actual+" not greater than lower bound, "+(expected-margin));
		assertTrue(actual<(expected+margin),"Actual value, "+actual+" not less than upper bound, "+(expected+margin));
	}
	
	private class ChangeGraphicEffectBySprite extends AllBadSprite {
		public double fx = 50;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadSprite#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadSprite#getEffect(java.lang.String)
		 */
		@Override
		public double getEffect(String name) {
			if("fx".equals(name))
				return fx;
			return 0.0;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadSprite#setEffect(java.lang.String, double)
		 */
		@Override
		public void setEffect(String name, double value) {
			if("fx".equals(name))
				fx = value;
		}
	}
}
