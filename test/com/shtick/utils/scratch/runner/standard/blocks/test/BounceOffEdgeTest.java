package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.core.Opcode.DataType;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.blocks.BounceOffEdge;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class BounceOffEdgeTest {

	@Test
	void testOpcode() {
		BounceOffEdge op = new BounceOffEdge();
		assertEquals("bounceOffEdge",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		BounceOffEdge op = new BounceOffEdge();
		assertArrayEquals(new DataType[] {}, op.getArgumentTypes());
	}

	@Test
	void testArgs() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,1,90);
		BounceOffEdge op = new BounceOffEdge();

		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[] {"test"});

		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
	}

	@Test
	void testNoBounce() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,1,90);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEquals(0, sprite.getScratchX());
		assertEquals(1, sprite.getScratchY());
		assertEquals(90, sprite.getDirection());
	}

	@Test
	void testBounceLeft() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(-240,1,-90);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEqualWithinMargin(-239, sprite.getScratchX(),0.0001);
		assertEquals(1, sprite.getScratchY());
		assertEquals(90, sprite.getDirection());
	}

	@Test
	void testBounceRight() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(240,1,90);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEqualWithinMargin(239, sprite.getScratchX(),0.0001);
		assertEquals(1, sprite.getScratchY());
		assertEquals(-90, sprite.getDirection());
	}

	@Test
	void testBounceLeftFail() { // Like testBounceLeft, but with the direction pointing into the stage already
		LocationDirectionSprite sprite = new LocationDirectionSprite(-240,1,90);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEqualWithinMargin(-240, sprite.getScratchX(),0.0001);
		assertEquals(1, sprite.getScratchY());
		assertEquals(90, sprite.getDirection());
	}

	@Test
	void testBounceRightFail() { // Like testBounceRight, but with the direction pointing into the stage already
		LocationDirectionSprite sprite = new LocationDirectionSprite(240,1,-90);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEqualWithinMargin(240, sprite.getScratchX(),0.0001);
		assertEquals(1, sprite.getScratchY());
		assertEquals(-90, sprite.getDirection());
	}

	@Test
	void testBounceTop() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,180,0);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEquals(0, sprite.getScratchX());
		assertEqualWithinMargin(179, sprite.getScratchY(),0.0001);
		assertEquals(180, sprite.getDirection());
	}

	@Test
	void testBounceBottom() {
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,-180,180);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEquals(0, sprite.getScratchX());
		assertEqualWithinMargin(-179, sprite.getScratchY(),0.0001);
		assertEquals(0, sprite.getDirection());
	}

	@Test
	void testBounceTopFail() { // Like testBounceTop, but with the direction pointing into the stage already
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,180,180);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEquals(0, sprite.getScratchX());
		assertEqualWithinMargin(180, sprite.getScratchY(),0.0001);
		assertEquals(180, sprite.getDirection());
	}

	@Test
	void testBounceBottomFail() { // Like testBounceBottom, but with the direction pointing into the stage already
		LocationDirectionSprite sprite = new LocationDirectionSprite(0,-180,0);
		BounceOffEdge op = new BounceOffEdge();
		op.execute(new TestRuntime(), new AllBadRunner(), sprite, new Object[0]);
		assertEquals(0, sprite.getScratchX());
		assertEqualWithinMargin(-180, sprite.getScratchY(),0.0001);
		assertEquals(0, sprite.getDirection());
	}

	private static void assertEqualWithinMargin(double expected, double actual, double margin) {
		assertTrue(actual>(expected-margin),"Actual value, "+actual+" not greater than lower bound, "+(expected-margin));
		assertTrue(actual<(expected+margin),"Actual value, "+actual+" not less than upper bound, "+(expected+margin));
	}

	public static class LocationDirectionSprite extends AllBadSprite {
		private Object lock = new Object();
		private double x;
		private double y;
		private double direction;
		
		/**
		 * @param x
		 * @param y
		 * @param direction
		 */
		public LocationDirectionSprite(double x, double y, double direction) {
			super();
			this.x = x;
			this.y = y;
			this.direction = direction;
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
			this.direction = direction;
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
		
		@Override
		public double getDirection() {
			return direction;
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

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#getStageWidth()
		 */
		@Override
		public int getStageWidth() {
			return 480;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#getStageHeight()
		 */
		@Override
		public int getStageHeight() {
			return 360;
		}
		
	}
}
