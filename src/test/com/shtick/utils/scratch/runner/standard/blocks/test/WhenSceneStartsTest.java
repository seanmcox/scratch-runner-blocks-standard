package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.core.elements.Stage;
import com.shtick.utils.scratch.runner.standard.blocks.WhenSceneStarts;
import com.shtick.utils.scratch.runner.standard.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.util.AllBadScriptTuple;
import com.shtick.utils.scratch.runner.standard.util.AllBadStage;

class WhenSceneStartsTest {

	@Test
	void testOpcode() {
		WhenSceneStarts op = new WhenSceneStarts();
		assertEquals("whenSceneStarts",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		WhenSceneStarts op = new WhenSceneStarts();
		assertArrayEquals(new DataType[] {DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		WhenSceneStarts op = new WhenSceneStarts();

		StageReportingRuntime runtime = new StageReportingRuntime();
		assertEquals(0,runtime.stage.listeners.size());
		op.applicationStarted(runtime);
		assertEquals(1,runtime.stage.listeners.size());
	}

// TODO Test are areSceneChangeScriptsRunning
//	@Test
//	void testNotifiers() {
//		WhenSceneStarts op = new WhenSceneStarts();
//
//		{ // Test expected use case
//			StageReportingRuntime runtime = new StageReportingRuntime(false);
//			op.applicationStarted(runtime);
//			StageListener stageListener = runtime.stage.listeners.iterator().next();
//			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
//			synchronized(notifier) {
//				stageListener.sceneChanged(0, "old", 1, "new");
//				long start = System.currentTimeMillis();
//				try {
//					notifier.wait(500);
//				}
//				catch(InterruptedException t) {
//					fail("Interrupted");
//				}
//				assertTrue((System.currentTimeMillis() - start)<500);
//			}
//			notifier = WhenSceneStarts.getSceneEventNotifier("new");
//			synchronized(notifier) {
//				stageListener.sceneChanged(1, "new", 0, "old");
//				long start = System.currentTimeMillis();
//				try {
//					notifier.wait(500);
//				}
//				catch(InterruptedException t) {
//					fail("Interrupted");
//				}
//				assertFalse((System.currentTimeMillis() - start)<500);
//			}
//		}
//
//		{ // Test misuse failure
//			StageReportingRuntime runtime = new StageReportingRuntime(false);
//			op.applicationStarted(runtime);
//			StageListener stageListener = runtime.stage.listeners.iterator().next();
//			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
//			synchronized(notifier) {
//				stageListener.sceneChanged(0, "old", 1, "new");
//				long start = System.currentTimeMillis();
//				try {
//					notifier.wait(500);
//				}
//				catch(InterruptedException t) {
//					fail("Interrupted");
//				}
//				assertTrue((System.currentTimeMillis() - start)<500);
//			}
//			stageListener.sceneChanged(1, "new", 0, "old");
//			// Reuse notifier. This is invalid
//			synchronized(notifier) {
//				stageListener.sceneChanged(0, "old", 1, "new");
//				long start = System.currentTimeMillis();
//				try {
//					notifier.wait(500);
//				}
//				catch(InterruptedException t) {
//					fail("Interrupted");
//				}
//				assertFalse((System.currentTimeMillis() - start)<500);
//			}
//		}
//	}

	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime();
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			assertNull(runtime.scriptRun);
			
			// Run unregistered
			Object notifier = new Object();
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(null, runtime.scriptRun);
			
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(null, runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run registered
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(newScriptTuple, runtime.scriptRun);
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(oldScriptTuple, runtime.scriptRun);
			
			runtime.scriptRun = null;
			op.unregisterListeningScript(newScriptTuple, new Object[] {"new"});
			op.unregisterListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run unregistered
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertNull(runtime.scriptRun);
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertNull(runtime.scriptRun);
		}
		
		{ // test unregister wrong name case.
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime();
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run registered
			Object notifier = new Object();
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(newScriptTuple, runtime.scriptRun);
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(oldScriptTuple, runtime.scriptRun);
			
			runtime.scriptRun = null;
			op.unregisterListeningScript(newScriptTuple, new Object[] {"new"});
			op.unregisterListeningScript(oldScriptTuple, new Object[] {"blah"});
			// Run unregistered
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertNull(runtime.scriptRun);
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(oldScriptTuple, runtime.scriptRun);
		}
		
		{ // test register wrong name case.
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime();
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"blah"});
			// Run registered
			Object notifier = new Object();
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertEquals(newScriptTuple, runtime.scriptRun);
			runtime.scriptRun = null;
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertNull(runtime.scriptRun);
		}
		
		{ // test register with bad parameters
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime();
			op.applicationStarted(runtime);
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			
			try {
				op.registerListeningScript(newScriptTuple, new Object[] {});
				fail("Exception expected.");
			}
			catch(Exception t) {
				// Expected.
			}
			{
				op.registerListeningScript(newScriptTuple, new Object[] {"one","two"});
			}
			try {
				op.registerListeningScript(newScriptTuple, new Object[] {null});
				fail("Exception expected.");
			}
			catch(Exception t) {
				// Expected.
			}
		}
		
		{ // test unregister with bad parameters
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime();
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

	@SuppressWarnings("serial")
	@Test
	void testConcurrency() {
		WhenSceneStarts op = new WhenSceneStarts();
		StageReportingRuntime runtime = new StageReportingRuntime();
		op.applicationStarted(runtime);
		StageListener stageListener = runtime.stage.listeners.iterator().next();
		ScriptTuple scriptTuple = new AllBadScriptTuple();

		assertEquals(0,runtime.runners.size());

		op.registerListeningScript(scriptTuple, new Object[] {"new"});
		// Run registered
		Object notifier = new Object();
		synchronized(notifier) {
			stageListener.sceneChanged(0, "old", 1, "new");
			try {
				notifier.wait(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
		}
		assertEquals(scriptTuple, runtime.scriptRun);

		assertEquals(1,runtime.runners.size()); // Runner was created.
		ScriptTupleRunner oldRunner = runtime.runners.values().iterator().next();

		synchronized(notifier) {
			stageListener.sceneChanged(0, "old", 1, "new");
			try {
				notifier.wait(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
		}
		assertEquals(1,runtime.runners.size()); // Runner was created.
		assertNotEquals(oldRunner, runtime.runners.values().iterator().next());
	}
	
	public static class StageReportingRuntime extends AllBadRuntime{
		public SceneReportingStage stage = new SceneReportingStage();
		public HashMap<ScriptTuple,ScriptTupleRunner> runners = new HashMap<>();
		public ScriptTuple scriptRun = null;

		public StageReportingRuntime() {
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#getCurrentStage()
		 */
		@Override
		public Stage getCurrentStage() {
			return stage;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#startScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, boolean)
		 */
		@Override
		public ScriptTupleRunner startScript(ScriptTuple script) {
			this.scriptRun = script;
			ScriptTupleRunner retval;
			retval = new AllBadRunner();
			runners.put(script,retval);
			return retval;
		}
	}
	
	public static class SceneReportingStage extends AllBadStage{
		public HashSet<StageListener> listeners = new HashSet<>();

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadStage#addStageListener(com.shtick.utils.scratch.runner.core.StageListener)
		 */
		@Override
		public void addStageListener(StageListener listener) {
			listeners.add(listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadStage#removeStageListener(com.shtick.utils.scratch.runner.core.StageListener)
		 */
		@Override
		public void removeStageListener(StageListener listener) {
			listeners.remove(listener);
		}
	}
}
