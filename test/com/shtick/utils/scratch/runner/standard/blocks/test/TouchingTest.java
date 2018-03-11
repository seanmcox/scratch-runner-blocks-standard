package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.core.elements.Sprite;
import com.shtick.utils.scratch.runner.standard.blocks.Touching;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class TouchingTest {

	@Test
	void testOpcode() {
		Touching op = new Touching();
		assertEquals("touching:",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		Touching op = new Touching();
		assertArrayEquals(new DataType[] {DataType.STRING}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		LocationDirectionSprite sprite1 = new LocationDirectionSprite("test1",0,1);
		LocationDirectionSprite sprite2 = new LocationDirectionSprite("test2",0,1);
		Touching op = new Touching();
		TestRuntime runtime = new TestRuntime();
		runtime.addSprite(sprite2);

		op.execute(runtime, new AllBadRunner(), sprite1, new Object[] {"test2"});

		try {
			op.execute(runtime, new AllBadRunner(), sprite1, new Object[] {"test3"});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected
		}

		try {
			op.execute(runtime, new AllBadRunner(), sprite1, new Object[0]);
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected
		}
	}

	@Test
	void testBasicTouch() {
		LocationDirectionSprite sprite1 = new LocationDirectionSprite("test1",0,0);
		LocationDirectionSprite sprite2 = new LocationDirectionSprite("test2",0,1);
		Touching op = new Touching();
		TestRuntime runtime = new TestRuntime();
		runtime.addSprite(sprite2);
		
		{
			Boolean result = (Boolean)op.execute(runtime, new AllBadRunner(), sprite1, new Object[]{"test2"});
			assertTrue(result);
		}
		
		{
			sprite2.setScratchY(5);
			Boolean result = (Boolean)op.execute(runtime, new AllBadRunner(), sprite1, new Object[]{"test2"});
			assertFalse(result);
		}
	}

	@Test
	void testCloneTouch() {
		LocationDirectionSprite sprite1 = new LocationDirectionSprite("test1",5,5);
		LocationDirectionSprite sprite2 = new LocationDirectionSprite("test2",0,1);
		Touching op = new Touching();
		TestRuntime runtime = new TestRuntime();
		runtime.addSprite(sprite2);
		
		{
			Boolean result = (Boolean)op.execute(runtime, new AllBadRunner(), sprite1, new Object[]{"test2"});
			assertFalse(result);
		}
		
		sprite2.clones.add(new LocationDirectionSprite("test2",5,5));
		{
			Boolean result = (Boolean)op.execute(runtime, new AllBadRunner(), sprite1, new Object[]{"test2"});
			assertTrue(result);
		}
	}

	public static class LocationDirectionSprite extends AllBadSprite {
		private Object lock = new Object();
		private String name;
		private double x;
		private double y;
		public boolean visible = true;
		public HashSet<Sprite> clones = new HashSet<>();
		
		/**
		 * @param name 
		 * @param x
		 * @param y
		 */
		public LocationDirectionSprite(String name, double x, double y) {
			super();
			this.name = name;
			this.x = x;
			this.y = y;
		}

		@Override
		public String getObjName() {
			return name;
		}

		@Override
		public Set<Sprite> getClones() {
			return clones;
		}

		@Override
		public void setScratchY(double scratchY) {
			y = scratchY;
		}
		
		@Override
		public void setScratchX(double scratchX) {
			x = scratchX;
		}
		
		@Override
		public void setDirection(double direction) {
			direction%=360;
			if(direction>180)
				direction-=360;
			else if(direction<-180)
				direction+=360;
		}
		
		@Override
		public boolean isVisible() {
			return visible;
		}
		
		@Override
		public void gotoXY(double scratchX, double scratchY) {
			x = scratchX;
			y = scratchY;
		}
		
		@Override
		public Area getSpriteShape() {
			Ellipse2D.Double shape = new Ellipse2D.Double(-1, -1, 2, 2);
			return new Area(shape);
		}
		
		@Override
		public Object getSpriteLock() {
			return lock;
		}
		
		@Override
		public Map<String, Object> getSpriteInfo() {
			fail("getSpriteInfo called unnecessarily");
			return null;
		}
		
		@Override
		public double getScratchY() {
			return y;
		}
		
		@Override
		public double getScratchX() {
			return x;
		}
		
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}
	}
	
	private static class TestRuntime extends AllBadRuntime{
		private HashMap<String, Sprite> spritesByName = new HashMap<>();
		
		public void addSprite(Sprite sprite) {
			spritesByName.put(sprite.getObjName(), sprite);
		}
		
		public void removeSpriteByName(String name) {
			spritesByName.remove(name);
		}

		@Override
		public Sprite getSpriteByName(String name) {
			return spritesByName.get(name);
		}

	}
}
