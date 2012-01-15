package de.iws.livingroom.domain.seeking.request.criteria;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

public abstract class LivingPeriodCriteria {
	
	private static class FromNowOn extends LivingPeriodCriteria {
		@Override
		public String toString() {
			return "from now on";
		}
	}
	
	public static LivingPeriodCriteria fromNowOn() {
		return new FromNowOn();
	}

	
	private static class StartDate extends LivingPeriodCriteria {

		private final DateMidnight startDate;

		public StartDate(DateMidnight startDate) {
			this.startDate = startDate;
		}
		
		@Override
		public String toString() {
			return "start date: " + startDate;
		}
	}
	
	public static LivingPeriodCriteria startDate(DateMidnight startDate) {
		return new StartDate(startDate);
	}
	
	private static class LimitedPeriod extends LivingPeriodCriteria {

		private final Interval interval;

		public LimitedPeriod(Interval interval) {
			this.interval = interval;
		}
		
		@Override
		public String toString() {
			return "limited period: " + interval;
		}

	}
	
	public static LivingPeriodCriteria limitedPeriod(Interval interval) {
		return new LimitedPeriod(interval);
	}
	
}
