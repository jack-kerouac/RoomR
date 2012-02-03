package models.common;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

public final class LivingPeriod {

	private final static DateMidnight UNLIMITED_PERIOD_END = new DateMidnight(2099, 1, 1);

	private final Interval timePeriod;

	public LivingPeriod(DateMidnight start) {
		super();
		this.timePeriod = new Interval(start, UNLIMITED_PERIOD_END);
	}

	public LivingPeriod(DateMidnight start, DateMidnight end) {
		this.timePeriod = new Interval(start, end);
	}

	public boolean isLimited() {
		return !timePeriod.getEnd().equals(UNLIMITED_PERIOD_END);
	}

	public boolean contains(LivingPeriod period) {
		return this.timePeriod.contains(period.timePeriod);
	}

	public DateMidnight getStartDate() {
		return new DateMidnight(timePeriod.getStart());
	}

	public DateMidnight getEndDate() {
		if (!isLimited())
			throw new RuntimeException("this period is unlimited");
		else
			return new DateMidnight(timePeriod.getEnd());
	}

}
