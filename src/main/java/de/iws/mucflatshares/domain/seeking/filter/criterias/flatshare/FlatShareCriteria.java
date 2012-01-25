package de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare;

import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.offering.flatshare.Address;
import de.iws.mucflatshares.domain.offering.flatshare.FlatShare;

public final class FlatShareCriteria implements Predicate<FlatShare> {

	private final String city;
	private final QuarterCriteria quaterCriteria;

	public FlatShareCriteria(String city, QuarterCriteria quaterCriteria) {
		super();
		this.city = city;
		this.quaterCriteria = quaterCriteria;
	}

	@Override
	public boolean apply(FlatShare flatShare) {
		Address address = flatShare.getAddress();
		return address.getCity().equals(city) && quaterCriteria.apply(flatShare.getQuarter());
	}
}
