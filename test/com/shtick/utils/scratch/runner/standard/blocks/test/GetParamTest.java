package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.standard.blocks.GetParam;
import com.shtick.utils.scratch.runner.standard.blocks.ProcDef;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadValueListener;

class GetParamTest {

	@Test
	void testOpcode() {
		GetParam op = new GetParam();
		assertEquals("getParam",op.getOpcode());
	}

	@Test
	void testArgs() {
		AllBadSprite sprite = new AllBadSprite();
		ProcDef.ProcedureContext context = new ProcDef.ProcedureContext(sprite, "Test %s", new String[] {"test"}, new Object[] {"value"});
		GetParam op = new GetParam();

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), context, new Object[] {"test","t"});
			assertEquals("value",retval);
		}

		{
			Object retval = op.execute(new AllBadRuntime(), new AllBadRunner(), context, new Object[] {"undefined","t"});
			assertEquals("",retval);
		}
		
		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), sprite, new Object[] {"test","t"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected Result.
		}

		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), context, new Object[] {"test"});
		}
		
		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), context, new Object[] {null,"t"});
		}
		
		{
			op.execute(new AllBadRuntime(), new AllBadRunner(), context, new Object[] {"test",null});
		}
	}

	@Test
	void testListener() {
		GetParam op = new GetParam();
		AllBadValueListener listener = new AllBadValueListener();
		op.addValueListener(listener);
		op.removeValueListener(listener);
	}
}
