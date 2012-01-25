package de.iws.mucflatshares.domain.common.noDomain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class InclusiveRangeTest {

	@Test
	public void testCreateRange() {
		new InclusiveRange<Integer>(0, 1);
		new InclusiveRange<Integer>(0, 0);

		try {
			new InclusiveRange<Integer>(1, 0);
			fail("can create range with start > end");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testContainsInt() {
		InclusiveRange<Integer> r = new InclusiveRange<Integer>(0, 2);
		assertFalse(r.contains(-1));
		assertTrue(r.contains(0));
		assertTrue(r.contains(1));
		assertTrue(r.contains(2));
		assertFalse(r.contains(3));
	}

	@Test
	public void testContainsNumericalRange() {
		InclusiveRange<Integer> r = new InclusiveRange<Integer>(0, 2);

		assertTrue(r.contains(new InclusiveRange<Integer>(0, 2)));
		assertTrue(r.contains(new InclusiveRange<Integer>(1, 2)));
		assertTrue(r.contains(new InclusiveRange<Integer>(1, 1)));
		assertTrue(r.contains(new InclusiveRange<Integer>(2, 2)));

		assertFalse(r.contains(new InclusiveRange<Integer>(0, 3)));
		assertFalse(r.contains(new InclusiveRange<Integer>(3, 3)));
		assertFalse(r.contains(new InclusiveRange<Integer>(2, 4)));
		assertFalse(r.contains(new InclusiveRange<Integer>(-1, 0)));
		assertFalse(r.contains(new InclusiveRange<Integer>(-2, -1)));
		assertFalse(r.contains(new InclusiveRange<Integer>(-1, 1)));
		assertFalse(r.contains(new InclusiveRange<Integer>(-1, 3)));
	}

}
