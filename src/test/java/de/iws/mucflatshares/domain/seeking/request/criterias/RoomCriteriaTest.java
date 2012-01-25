package de.iws.mucflatshares.domain.seeking.request.criterias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.iws.mucflatshares.domain.common.FloorSpace;
import de.iws.mucflatshares.domain.offering.offer.Room;
import de.iws.mucflatshares.domain.seeking.filter.criterias.RoomCriteria;

public class RoomCriteriaTest {

	@Test
	public void testApply() {
		RoomCriteria c = RoomCriteria.minFloorSpace(new FloorSpace(20));

		assertTrue(c.apply(new Room(new FloorSpace(20))));
		assertTrue(c.apply(new Room(new FloorSpace(25))));

		assertFalse(c.apply(new Room(new FloorSpace(19))));
	}

}
