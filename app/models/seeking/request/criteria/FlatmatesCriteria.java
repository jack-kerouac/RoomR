package models.seeking.request.criteria;

public class FlatmatesCriteria {

	private static class Unspecified extends FlatmatesCriteria {
	}

	public static FlatmatesCriteria unspecified() {
		return new Unspecified();
	}

}
