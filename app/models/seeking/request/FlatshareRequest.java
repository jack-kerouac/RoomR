package models.seeking.request;

import java.util.Collection;

import models.common.FloorSpace;
import models.offering.flatshare.Quarter;
import models.seeking.request.criteria.FlatmatesCriteria;
import models.seeking.request.criteria.LivingPeriodCriteria;
import models.seeking.request.criteria.LocationCriteria;
import models.seeking.request.criteria.PriceCriteria;
import models.seeking.request.criteria.RoomCriteria;

import org.joda.money.Money;

public final class FlatshareRequest {

	public final static class Builder {
		private LivingPeriodCriteria livingPeriodCriteria = LivingPeriodCriteria.fromNowOn();
		private PriceCriteria priceCriteria = PriceCriteria.unlimitedMonthlyTotal();
		private RoomCriteria roomCriteria = RoomCriteria.arbitraryFloorSpace();
		private LocationCriteria locationCriteria = LocationCriteria.everywhere();
		private FlatmatesCriteria flatmatesCriteria = FlatmatesCriteria.unspecified();

		private Builder(LivingPeriodCriteria livingPeriodCriteria) {
			this.livingPeriodCriteria = livingPeriodCriteria;
		}

		public Builder maxTotalPricePerMonth(Money maxTotalPerMonth) {
			this.priceCriteria = PriceCriteria.limitedMonthlyTotal(maxTotalPerMonth);
			return this;
		}

		public Builder minRoomFloorSpace(FloorSpace space) {
			this.roomCriteria = RoomCriteria.minFloorSpace(space);
			return this;
		}

		public Builder quarters(Collection<Quarter> selection) {
			this.locationCriteria = LocationCriteria.inQuarters(selection);
			return this;
		}

		public FlatshareRequest build() {
			return new FlatshareRequest(livingPeriodCriteria, priceCriteria, roomCriteria, locationCriteria,
					flatmatesCriteria);
		}
	}

	public static Builder builder(LivingPeriodCriteria livingPeriodCriteria) {
		return new Builder(livingPeriodCriteria);
	}

	private LivingPeriodCriteria periodCriteria;
	private PriceCriteria priceCriteria;
	private RoomCriteria roomCriteria;
	private LocationCriteria locationCriteria;
	private FlatmatesCriteria flatmatesCriteria;

	public FlatshareRequest(LivingPeriodCriteria periodCriteria, PriceCriteria priceCriteria,
			RoomCriteria roomCriteria, LocationCriteria locationCriteria, FlatmatesCriteria flatmatesCriteria) {
		super();
		this.periodCriteria = periodCriteria;
		this.priceCriteria = priceCriteria;
		this.roomCriteria = roomCriteria;
		this.locationCriteria = locationCriteria;
		this.flatmatesCriteria = flatmatesCriteria;
	}

}
