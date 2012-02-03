package models.seeking.request.criteria;

import java.util.Collection;

import models.offering.flatshare.Quarter;

public class LocationCriteria {

	private static class Everywhere extends LocationCriteria {
		@Override
		public String toString() {
			return "everywhere";
		}
	}

	public static LocationCriteria everywhere() {
		return new Everywhere();
	}

	
	private static class InQuarters extends LocationCriteria {

		private final Collection<Quarter> selection;

		public InQuarters(Collection<Quarter> selection) {
			this.selection = selection;
		}

		@Override
		public String toString() {
			return "in one of the quarters " + selection;
		}
	}
	
	public static LocationCriteria inQuarters(Collection<Quarter> selection) {
		return new InQuarters(selection);
	}

}
