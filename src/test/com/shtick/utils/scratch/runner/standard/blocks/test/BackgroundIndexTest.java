package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.Stage;
import com.shtick.utils.scratch.runner.standard.blocks.BackgroundIndex;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadSprite;
import com.shtick.utils.scratch.runner.standard.util.AllBadStage;

class BackgroundIndexTest {

	@Test
	void testOpcode() {
		BackgroundIndex op = new BackgroundIndex();
		assertEquals("backgroundIndex",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		BackgroundIndex op = new BackgroundIndex();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
	}

	@Test
	void testExecute() {
		StagingRuntime runtime = new StagingRuntime();
		AllBadSprite sprite = new AllBadSprite();
		BackgroundIndex op = new BackgroundIndex();

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {});
			assertNotNull(result);
			assertEquals(0L,result);
		}
		
		BackgroundingStage stage = new BackgroundingStage();
		runtime.stage = stage;
		stage.index = 12;

		{
			Object result = op.execute(runtime, new AllBadRunner(), sprite, new Object[] {});
			assertNotNull(result);
			assertEquals(12L,result);
		}
	}
	
	private class StagingRuntime extends AllBadRuntime{
		BackgroundingStage stage;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadRuntime#getCurrentStage()
		 */
		@Override
		public Stage getCurrentStage() {
			return stage;
		}
		
	}
	
	private class BackgroundingStage extends AllBadStage{
		long index = 255;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.util.AllBadStage#getContextPropertyValueByName(java.lang.String)
		 */
		@Override
		public Object getContextPropertyValueByName(String name) throws IllegalArgumentException {
			if(!"background#".equals(name))
				throw new UnsupportedOperationException("Context property value, "+name+", not supported.");
			return index;
		}
	}
}
