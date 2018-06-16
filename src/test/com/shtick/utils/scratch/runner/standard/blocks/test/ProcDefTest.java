package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.ProcDef;
import com.shtick.utils.scratch.runner.standard.blocks.ProcDef.ProcedureContext;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class ProcDefTest {

	@Test
	void testOpcode() {
		ProcDef op = new ProcDef();
		assertEquals("procDef",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ProcDef op = new ProcDef();
		assertArrayEquals(new DataType[] {DataType.STRING,DataType.TUPLE,DataType.TUPLE,DataType.BOOLEAN}, op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		ProcDef op = new ProcDef();
		op.applicationStarted(new AllBadRuntime());
	}

	@Test
	void testScriptRegistration() {
		AllBadSprite sprite = new AllBadSprite();
		ProcDef op = new ProcDef();
		
		ContextySprite context1 = new ContextySprite("A");
		ContextySprite context2 = new ContextySprite("A"); // Use same names to simulate clones. Should register scripts based on object, not object name.
		ContextyScriptTuple script1 = new ContextyScriptTuple(context1);
		ContextyScriptTuple script2 = new ContextyScriptTuple(context2);
		String scriptName1 = "testName1";
		String scriptName2 = "testName2";

		{ // Test initial conditions
			try {
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			try {
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			try {
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			try {
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
		}
		
		op.registerListeningScript(script1, new Object[] {scriptName1,new Object[0],new Object[0],false});
		{ // Test with one script added to a single object
			{
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
			
			try {
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			try {
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			try {
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
		}
		
		op.registerListeningScript(script2, new Object[] {scriptName1,new Object[0],new Object[0],false});
		{ // Test with same name script on different object with the same name
			{
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
			
			try {
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
			
			{
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script2,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			try {
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
		}
		
		op.registerListeningScript(script1, new Object[] {scriptName2,new Object[0],new Object[0],false});
		{ // Test with two scripts on the same object.
			{
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
			
			{
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			{
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script2,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			try {
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}
		}
		
		op.registerListeningScript(script2, new Object[] {scriptName2,new Object[0],new Object[0],true});
		{ // Test with an atomic script
			{
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
			
			{
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			{
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script2,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			{
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				assertNotNull(subaction);
				assertTrue(subaction.isSubscriptAtomic());
				assertEquals(script2,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
		}
		
		op.unregisterListeningScript(script2, new Object[] {scriptName1,new Object[0],new Object[0],false});
		{ // Test unregister
			{
				OpcodeSubaction subaction = op.call(context1, scriptName1, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
			
			{
				OpcodeSubaction subaction = op.call(context1, scriptName2, new Object[0]);
				assertNotNull(subaction);
				assertFalse(subaction.isSubscriptAtomic());
				assertEquals(script1,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}

			try {
				OpcodeSubaction subaction = op.call(context2, scriptName1, new Object[0]);
				fail("Call succeeded when expected to fail.");
			}
			catch(Exception t) {
				// Expected
			}

			{
				OpcodeSubaction subaction = op.call(context2, scriptName2, new Object[0]);
				assertNotNull(subaction);
				assertTrue(subaction.isSubscriptAtomic());
				assertEquals(script2,subaction.getSubscript());
				assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
			}
		}
	}
	
	private class ContextyScriptTuple extends AllBadScriptTuple {
		public ScriptContext contextObject;

		public ContextyScriptTuple(ScriptContext contextObject) {
			this.contextObject = contextObject;
		}

		@Override
		public ScriptContext getContext() {
			return contextObject;
		}

		@Override
		public ScriptTuple clone(ScriptContext context) {
			assertTrue(context instanceof ProcedureContext);
			assertEquals(contextObject,context.getContextObject());
			return this; // Don't really clone. Just check to see that we get the right stuff to be able to clone.
		}
	}
	
	private class ContextySprite extends AllBadSprite{
		public String objName;
		public ScriptContext contextObject;

		public ContextySprite(String objName) {
			this.objName = objName;
			this.contextObject = this;
		}

		public ContextySprite(String objName, ScriptContext contextObject) {
			this.objName = objName;
			this.contextObject = contextObject;
		}

		@Override
		public ScriptContext getContextObject() {
			return contextObject;
		}

		@Override
		public String getObjName() {
			return objName;
		}

		@Override
		public boolean isClone() {
			return false;
		}
	}
}
