package de.iws.livingroom.domain.offering.offer.criteria;

import com.google.common.base.Predicate;

import de.iws.livingroom.domain.common.Age;
import de.iws.livingroom.domain.common.noDomain.InclusiveRange;

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
