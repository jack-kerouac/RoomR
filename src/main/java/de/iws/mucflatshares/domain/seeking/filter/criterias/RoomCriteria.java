package de.iws.mucflatshares.domain.seeking.filter.criterias;

import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.common.FloorSpace;
import de.iws.mucflatshares.domain.offering.offer.Room;

public abstract class RoomCriteria implements Predicate<Room> {

	public final static RoomCriteria arbitraryFloorSpace() {
		return new ArbitraryFloorSpace();
	}

	private final static class ArbitraryFloorSpace extends RoomCriteria {

		@Override
		public boolean apply(Room input) {
			return true;
		}

	}

	public final static RoomCriteria minFloorSpace(FloorSpace minSize) {
		return new MinFloorSpace(minSize);
	}

	private final static class MinFloorSpace extends RoomCriteria {

		public final FloorSpace minSize;

		public MinFloorSpace(FloorSpace minSize) {
			this.minSize = minSize;
		}

		@Override
		public boolean apply(Room room) {
			return room.isAtLeast(minSize);
		}
	}

}
