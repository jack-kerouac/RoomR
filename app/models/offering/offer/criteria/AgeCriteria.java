package models.offering.offer.criteria;

import models.common.Age;
import models.common.noDomain.InclusiveRange;

import com.google.common.base.Predicate;

public class AgeCriteria implements Predicate<Age> {

	private final InclusiveRange<Age> ages;

	public AgeCriteria() {
		ages = new InclusiveRange<Age>(new Age(0), new Age(99));
	}

	public AgeCriteria(Age min, Age max) {
		ages = new InclusiveRange<Age>(min, max);
	}

	@Override
	public boolean apply(Age age) {
		return ages.contains(age);
	}
	
	public Age getStartAge() {
		return ages.getStart();
	}
	
	public Age getEndAge() {
		return ages.getEnd();
	}

}
