package de.iws.mucflatshares.domain.offering.flatshare;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateMidnight;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.iws.mucflatshares.domain.common.Age;
import de.iws.mucflatshares.domain.common.Gender;
import de.iws.mucflatshares.domain.common.Language;

public class ResidentTest {

	@Test
	public void testGetAge() {
		Resident florian = new Resident(Gender.male, new DateMidnight(1984, 7, 31), ImmutableSet.of(new Language("English")));
		
		assertEquals(new Age(27), florian.calculateAge(new DateMidnight(2011, 8, 15)));
	}
}
