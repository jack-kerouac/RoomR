package de.iws.mucflatshares.domain.offering.offer.criteria;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.common.Gender;

public class GenderCriteria implements Predicate<Gender> {

	private final boolean maleAllowed;
	private final boolean femaleAllowed;

	public GenderCriteria(boolean maleAllowed, boolean femaleAllowed) {
		Preconditions.checkArgument(maleAllowed || femaleAllowed,
				"either male or female or both genders must be allowed!");

		this.maleAllowed = maleAllowed;
		this.femaleAllowed = femaleAllowed;
	}

	@Override
	public boolean apply(Gender gender) {
		switch (gender) {
		case female:
			return femaleAllowed;
		case male:
			return maleAllowed;
		default:
			throw new RuntimeException("THIS SHOULD NOT HAPPEN, gender value " + gender);
		}
	}

	public boolean isMaleAllowed() {
		return maleAllowed;
	}

	public boolean isFemaleAllowed() {
		return femaleAllowed;
	}
}
