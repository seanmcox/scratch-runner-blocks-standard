package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.StageListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptTuple;
import com.shtick.utils.scratch.runner.core.elements.Stage;
import com.shtick.utils.scratch.runner.standard.blocks.WhenSceneStarts;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadScriptTuple;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadStage;

class WhenSceneStartsTest {

	@Test
	void testOpcode() {
		WhenSceneStarts op = new WhenSceneStarts();
		assertEquals("whenSceneStarts",op.getOpcode());
	}

	@Test
	void testApplicationStarted() {
		WhenSceneStarts op = new WhenSceneStarts();

		StageReportingRuntime runtime = new StageReportingRuntime(false);
		assertEquals(0,runtime.stage.listeners.size());
		op.applicationStarted(runtime);
		assertEquals(1,runtime.stage.listeners.size());
	}

	@Test
	void testNotifiers() {
		WhenSceneStarts op = new WhenSceneStarts();

		{ // Test expected use case
			StageReportingRuntime runtime = new StageReportingRuntime(false);
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				long start = System.currentTimeMillis();
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
				assertTrue((System.currentTimeMillis() - start)<500);
			}
			notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				long start = System.currentTimeMillis();
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
				assertFalse((System.currentTimeMillis() - start)<500);
			}
		}

		{ // Test misuse failure
			StageReportingRuntime runtime = new StageReportingRuntime(false);
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				long start = System.currentTimeMillis();
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
				assertTrue((System.currentTimeMillis() - start)<500);
			}
			stageListener.sceneChanged(1, "new", 0, "old");
			// Reuse notifier. This is invalid
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				long start = System.currentTimeMillis();
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
				assertFalse((System.currentTimeMillis() - start)<500);
			}
		}
	}

	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime(false);
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			// Run unregistered
			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertEquals(null, runtime.scriptRun);
			
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertEquals(null, runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run registered
			notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(newScriptTuple, runtime.scriptRun);
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(oldScriptTuple, runtime.scriptRun);
			
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			op.unregisterListeningScript(newScriptTuple, new Object[] {"new"});
			op.unregisterListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run unregistered
			notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
		}
		
		{ // test unregister wrong name case.
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime(false);
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"old"});
			// Run registered
			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(newScriptTuple, runtime.scriptRun);
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(oldScriptTuple, runtime.scriptRun);
			
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			op.unregisterListeningScript(newScriptTuple, new Object[] {"new"});
			op.unregisterListeningScript(oldScriptTuple, new Object[] {"blah"});
			// Run unregistered
			notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(oldScriptTuple, runtime.scriptRun);
		}
		
		{ // test register wrong name case.
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime(false);
			op.applicationStarted(runtime);
			StageListener stageListener = runtime.stage.listeners.iterator().next();
			ScriptTuple newScriptTuple = new AllBadScriptTuple();
			ScriptTuple oldScriptTuple = new AllBadScriptTuple();
			
			runtime.isAtomic = true;
			assertNull(runtime.scriptRun);
			
			op.registerListeningScript(newScriptTuple, new Object[] {"new"});
			op.registerListeningScript(oldScriptTuple, new Object[] {"blah"});
			// Run registered
			Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
			synchronized(notifier) {
				stageListener.sceneChanged(0, "old", 1, "new");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertFalse(runtime.isAtomic);
			assertEquals(newScriptTuple, runtime.scriptRun);
			runtime.isAtomic = true;
			runtime.scriptRun = null;
			notifier = WhenSceneStarts.getSceneEventNotifier("old");
			synchronized(notifier) {
				stageListener.sceneChanged(1, "new", 0, "old");
				try {
					notifier.wait(500);
				}
				catch(InterruptedException t) {
					fail("Interrupted");
				}
			}
			assertTrue(runtime.isAtomic);
			assertNull(runtime.scriptRun);
		}
		
		{ // test register with bad parameters
			WhenSceneStarts op = new WhenSceneStarts();
			StageReportingRuntime runtime = new StageReportingRuntime(false);
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
			StageReportingRuntime runtime = new StageReportingRuntime(false);
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
		StageReportingRuntime runtime = new StageReportingRuntime(true);
		op.applicationStarted(runtime);
		StageListener stageListener = runtime.stage.listeners.iterator().next();
		ScriptTuple scriptTuple = new AllBadScriptTuple();

		assertEquals(0,runtime.runners.size());

		op.registerListeningScript(scriptTuple, new Object[] {"new"});
		// Run registered
		Object notifier = WhenSceneStarts.getSceneEventNotifier("new");
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
		ScriptTupleRunner oldRunner = runtime.runners.iterator().next();
		assertFalse(oldRunner.isStopFlagged());

		notifier = WhenSceneStarts.getSceneEventNotifier("new");
		synchronized(notifier) {
			stageListener.sceneChanged(0, "old", 1, "new");
			try {
				notifier.wait(500);
			}
			catch(InterruptedException t) {
				fail("Interrupted");
			}
		}
		assertEquals(2,runtime.runners.size()); // Runner was created.
		assertTrue(oldRunner.isStopFlagged());
		runtime.runners.remove(oldRunner);
		oldRunner = runtime.runners.iterator().next();
		assertFalse(oldRunner.isStopFlagged());
		oldRunner.flagStop();
	}
	
	public static class StageReportingRuntime extends AllBadRuntime{
		public SceneReportingStage stage = new SceneReportingStage();
		public HashSet<ScriptTupleRunner> runners = new HashSet<>();
		public ScriptTuple scriptRun = null;
		public boolean isAtomic;
		private boolean isJoining = false;

		public StageReportingRuntime(boolean isJoining) {
			this.isJoining = isJoining;
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
		public ScriptTupleRunner startScript(ScriptTuple script, boolean isAtomic) {
			this.scriptRun = script;
			this.isAtomic = isAtomic;
			ScriptTupleRunner retval;
			if(isJoining) {
				retval = new JoiningScriptTupleRunner();
				runners.add(retval);
			}
			else {
				retval = new UnjoiningScriptTupleRunner();
			}
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
	
	public static class JoiningScriptTupleRunner extends AllBadRunner {
		private final Object LOCK = new Object();
		private boolean stopFlagged = false;

		@Override
		public void flagStop() {
			synchronized(LOCK) {
				LOCK.notifyAll();
				stopFlagged = true;
			}
		}

		@Override
		public boolean isStopFlagged() {
			return stopFlagged;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner#join(long, int)
		 */
		@Override
		public void join(long millis, int nanos) throws InterruptedException {
			Thread.sleep(millis);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner#join()
		 */
		@Override
		public void join() throws InterruptedException {
			synchronized(LOCK) {
				LOCK.wait();
			}
		}
	}
}
