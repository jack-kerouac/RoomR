package de.iws.mucflatshares.domain.offering.offer.criteria;

import static org.junit.Assert.*;

import org.junit.Test;

import static de.iws.mucflatshares.domain.common.Gender.male;
import static de.iws.mucflatshares.domain.common.Gender.female;


public class GenderCriteriaTest {

	@Test
	public void testAllows() {
		GenderCriteria onlyMale = new GenderCriteria(true, false);
		assertTrue(onlyMale.apply(male));
		assertFalse(onlyMale.apply(female));
		
		GenderCriteria onlyFemale = new GenderCriteria(false, true);
		assertFalse(onlyFemale.apply(male));
		assertTrue(onlyFemale.apply(female));
		
		GenderCriteria both = new GenderCriteria(true, true);
		assertTrue(both.apply(male));
		assertTrue(both.apply(female));
	}
	

}
