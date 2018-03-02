package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.WhenKeyPressed;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadScriptTuple;

class WhenKeyPressedTest {

	@Test
	void testOpcode() {
		WhenKeyPressed op = new WhenKeyPressed();
		assertEquals("whenKeyPressed",op.getOpcode());
	}

	@Test
	void testApplicationStarted() {
		WhenKeyPressed op = new WhenKeyPressed();

		KeyReportingRuntime runtime = new KeyReportingRuntime();
		assertEquals(0,runtime.listeners.size());
		op.applicationStarted(runtime);
		assertEquals(1,runtime.listeners.size());
	}

	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			WhenKeyPressed op = new WhenKeyPressed();
			KeyReportingRuntime runtime = new KeyReportingRuntime();
			op.applicationStarted(runtime);
			KeyListener keyListener = runtime.listeners.iterator().next();
			ScriptTuple scriptTuple0 = new AllBadScriptTuple();
			ScriptTuple scriptTuple1 = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			// Run unregistered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertTrue(runtime.isAtomic);
			assertEquals(null, runtime.scriptRun);
			
			op.registerListeningScript(scriptTuple0, new Object[] {"0"});
			op.registerListeningScript(scriptTuple1, new Object[] {"1"});
			// Run registered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple0, runtime.scriptRun);
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '1'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple1, runtime.scriptRun);
			
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			op.unregisterListeningScript(scriptTuple0, new Object[] {"0"});
			op.unregisterListeningScript(scriptTuple1, new Object[] {"1"});
			// Run unregistered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '1'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
		}
		
		{ // test unregister wrong name case.
			WhenKeyPressed op = new WhenKeyPressed();
			KeyReportingRuntime runtime = new KeyReportingRuntime();
			op.applicationStarted(runtime);
			KeyListener keyListener = runtime.listeners.iterator().next();
			ScriptTuple scriptTuple0 = new AllBadScriptTuple();
			ScriptTuple scriptTuple1 = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(scriptTuple0, new Object[] {"0"});
			op.registerListeningScript(scriptTuple1, new Object[] {"1"});
			// Run registered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple0, runtime.scriptRun);
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '1'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple1, runtime.scriptRun);
			
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			op.unregisterListeningScript(scriptTuple0, new Object[] {"0"});
			op.unregisterListeningScript(scriptTuple1, new Object[] {"3"});
			// Run unregistered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '1'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple1, runtime.scriptRun);
		}
		
		{ // test register wrong name case.
			WhenKeyPressed op = new WhenKeyPressed();
			KeyReportingRuntime runtime = new KeyReportingRuntime();
			op.applicationStarted(runtime);
			KeyListener keyListener = runtime.listeners.iterator().next();
			ScriptTuple scriptTuple0 = new AllBadScriptTuple();
			ScriptTuple scriptTuple1 = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(scriptTuple0, new Object[] {"0"});
			op.registerListeningScript(scriptTuple1, new Object[] {"3"});
			// Run registered
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '0'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertFalse(runtime.isAtomic);
			assertEquals(scriptTuple0, runtime.scriptRun);
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			keyListener.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_0, '1'));
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
		}
		
		{ // test register with bad parameters
			WhenKeyPressed op = new WhenKeyPressed();
			KeyReportingRuntime runtime = new KeyReportingRuntime();
			op.applicationStarted(runtime);
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			
			try {
				op.registerListeningScript(newScriptTuple, new Object[] {});
				fail("IllegalArgumentException expected.");
			}
			catch(Exception t) {
				// Expected.
			}
			{
				op.registerListeningScript(newScriptTuple, new Object[] {"one","two"});
			}
			try {
				op.registerListeningScript(newScriptTuple, new Object[] {null});
				fail("IllegalArgumentException expected.");
			}
			catch(Exception t) {
				// Expected.
			}
		}
		
		{ // test unregister with bad parameters
			WhenKeyPressed op = new WhenKeyPressed();
			KeyReportingRuntime runtime = new KeyReportingRuntime();
			op.applicationStarted(runtime);
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			try {
				op.unregisterListeningScript(newScriptTuple, new Object[] {});
				fail("Exception expected.");
			}
			catch(Exception t) {
				// Expected.
			}
			{
				op.unregisterListeningScript(newScriptTuple, new Object[] {"one","two"});
			}
			try {
				op.unregisterListeningScript(newScriptTuple, new Object[] {null});
				fail("Exception expected.");
			}
			catch(Exception t) {
				// Expected.
			}
		}
	}
	
	public static class KeyReportingRuntime extends AllBadRuntime{
		public HashSet<KeyListener> listeners = new HashSet<>();
		public ScriptTuple scriptRun = null;
		public boolean isAtomic;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#addKeyListener(java.awt.event.KeyListener)
		 */
		@Override
		public void addKeyListener(KeyListener listener) {
			listeners.add(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#removeKeyListener(java.awt.event.KeyListener)
		 */
		@Override
		public void removeKeyListener(KeyListener listener) {
			listeners.remove(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#startScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, boolean)
		 */
		@Override
		public ScriptTupleRunner startScript(ScriptTuple script, boolean isAtomic) {
			this.scriptRun = script;
			this.isAtomic = isAtomic;
			return new UnjoiningScriptTupleRunner();
		}
	}
	
	public static class UnjoiningScriptTupleRunner extends AllBadRunner {

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner#join(long, int)
		 */
		@Override
		public void join(long millis, int nanos) throws InterruptedException {
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner#join()
		 */
		@Override
		public void join() throws InterruptedException {
		}
	}
}
