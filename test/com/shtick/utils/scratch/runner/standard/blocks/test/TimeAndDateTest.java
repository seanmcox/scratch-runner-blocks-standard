package com.shtick.utils.scratch.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch.runner.standard.blocks.TimeAndDate;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch.runner.standard.blocks.util.AllBadSprite;

class TimeAndDateTest {

	@Test
	void testOpcode() {
		TimeAndDate op = new TimeAndDate();
		assertEquals("timeAndDate",op.getOpcode());
	}

	@Test
	void testArgs() {
		TimeAndDate op = new TimeAndDate();

		op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"test"});

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[0]);
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected
		}

		try {
			op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {1.5});
			fail("Exception expected.");
		}
		catch(Exception t) {
			// Expected
		}
	}

	@Test
	void testSeconds() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"second"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getSecond();
		int ketValue = ket.getSecond();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testMinutes() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"minute"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getMinute();
		int ketValue = ket.getMinute();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testHours() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"hour"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getHour();
		int ketValue = ket.getHour();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testDayOfWeek() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"day of week"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getDayOfWeek().getValue();
		int ketValue = ket.getDayOfWeek().getValue();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testDate() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"date"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getDayOfMonth();
		int ketValue = ket.getDayOfMonth();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testMonth() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"month"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getMonth().getValue();
		int ketValue = ket.getMonth().getValue();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}

	@Test
	void testYears() {
		TimeAndDate op = new TimeAndDate();
		LocalDateTime bra = LocalDateTime.now();
		Number result = (Number)op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadSprite(), new Object[] {"year"});
		LocalDateTime ket = LocalDateTime.now();
		assertTrue((result instanceof Long)||(result instanceof Integer));
		int braValue = bra.getYear();
		int ketValue = ket.getYear();
		if(braValue<ketValue)
			assertTrue((result.intValue()>=braValue)&&(result.intValue()<=ketValue));
		else
			assertTrue((result.intValue()>=braValue)||(result.intValue()<=ketValue));
	}
}
