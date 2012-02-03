package models.offering.flatshare;

import java.util.Collection;

import models.common.Age;
import models.common.Gender;
import models.common.Language;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

public class Resident {
	private final Gender gender;

	private final DateMidnight birthDay;

	private final Collection<Language> spokenLanguages;

	public Resident(Gender gender, DateMidnight birthDay, Collection<Language> spokenLanguages) {
		super();
		this.gender = gender;
		this.birthDay = birthDay;
		this.spokenLanguages = spokenLanguages;
	}

	public Gender getGender() {
		return gender;
	}

	public Age calculateAge(DateMidnight atDate) {
		Interval life = new Interval(birthDay, atDate);
		return new Age(life.toPeriod().getYears());
	}

	/**
	 * Calculates the current age of the resident.
	 */
	public Age getAge() {
		return calculateAge(new DateMidnight());
	}

	public Collection<Language> getSpokenLanguages() {
		return spokenLanguages;
	}

}
