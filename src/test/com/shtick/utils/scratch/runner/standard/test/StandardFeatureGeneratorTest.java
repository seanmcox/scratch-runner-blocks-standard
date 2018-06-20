package com.shtick.utils.scratch.runner.standard.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.FeatureSet;
import com.shtick.utils.scratch.runner.standard.StandardFeatureGenerator;

class StandardFeatureGeneratorTest {

	@Test
	void testConstructor() {
		@SuppressWarnings("unused")
		StandardFeatureGenerator gen = new StandardFeatureGenerator();
	}

	@Test
	void testGenerateFeatureSet() {
		StandardFeatureGenerator gen = new StandardFeatureGenerator();
		FeatureSet featureSet = gen.generateFeatureSet();
		assertNotNull(featureSet);
		assertTrue(featureSet.getGraphicEffects().size()>0);
		assertTrue(featureSet.getOpcodes().size()>0);
		assertTrue(featureSet.getStageMonitorCommands().size()>0);
	}

	@Test
	void testGetFeatureSetName() {
		StandardFeatureGenerator gen = new StandardFeatureGenerator();
		assertEquals("standard", gen.getFeatureSetName());
	}

	@Test
	void testCreateTalkBubbleImage() {
		assertNotNull(StandardFeatureGenerator.createTalkBubbleImage("test"));
	}

	@Test
	void testCreateTextImage() {
		assertNotNull(StandardFeatureGenerator.createTextImage("test"));
	}

	@Test
	void testCreateThoughtBubbleImage() {
		assertNotNull(StandardFeatureGenerator.createThoughtBubbleImage("test"));
	}

	@Test
	void testGetKeyIdForEvent() {
		assertEquals("A",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_A,'A')));
		assertEquals("a",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_A,'a')));
		assertEquals("1",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_1,'1')));
		assertEquals("space",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_SPACE,' ')));
		assertEquals("up arrow",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_UP,'?')));
		assertEquals("down arrow",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_DOWN,'?')));
		assertEquals("left arrow",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_LEFT,'?')));
		assertEquals("right arrow",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_RIGHT,'?')));
		assertEquals("enter",StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_ENTER,'?')));
		assertNull(StandardFeatureGenerator.getKeyIdForEvent(new CharKeyEvent(KeyEvent.VK_CAPS_LOCK,'?')));
	}
	
	private class CharKeyEvent extends KeyEvent {

		public CharKeyEvent(int keyCode, char keyChar) {
			super(new JLabel(""), 0, 0, 0, keyCode, keyChar);
		}
	}
}
