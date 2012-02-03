package models.seeking.request.criteria;

import models.common.FloorSpace;

/**
 * Not only about the room itself, about the whole flat as well. Currently, onlz
 * captures floor space.
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public class RoomCriteria {

	private static class Arbitray extends RoomCriteria {
		@Override
		public String toString() {
			return "arbitrary size";
		}
	}

	public static RoomCriteria arbitraryFloorSpace() {
		return new Arbitray();
	}

	private static class MinFloorSpace extends RoomCriteria {

		private final FloorSpace space;

		public MinFloorSpace(FloorSpace space) {
			this.space = space;
		}

		@Override
		public String toString() {
			return "minimal floor space: " + space;
		}
	}

	public static RoomCriteria minFloorSpace(FloorSpace space) {
		return new MinFloorSpace(space);
	}

}
