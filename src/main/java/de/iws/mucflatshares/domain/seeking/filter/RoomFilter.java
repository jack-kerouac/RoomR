package de.iws.mucflatshares.domain.seeking.filter;

import java.util.Collection;

import org.joda.money.Money;

import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.common.FloorSpace;
import de.iws.mucflatshares.domain.common.LivingPeriod;
import de.iws.mucflatshares.domain.offering.flatshare.Quarter;
import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.seeking.filter.criterias.PricingCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.RoomCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare.FlatShareCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare.QuarterCriteria;

public final class RoomFilter implements Predicate<RoomOffer> {

	public final static class Builder {

		private final LivingPeriod livingPeriod;

		private final String city;

		private QuarterCriteria quarterCriteria = QuarterCriteria.all();
		private RoomCriteria roomCriteria = RoomCriteria.arbitraryFloorSpace();
		private PricingCriteria pricingCriteria = PricingCriteria.unlimitedMonthlyTotal();

		private Builder(LivingPeriod livingPeriod, String city) {
			this.city = city;
			this.livingPeriod = livingPeriod;
		}

		public Builder maxTotalPricePerMonth(Money maxTotalPerMonth) {
			this.pricingCriteria = PricingCriteria.limitedMonthlyTotal(maxTotalPerMonth);
			return this;
		}

		public Builder minRoomFloorSpace(FloorSpace space) {
			this.roomCriteria = RoomCriteria.minFloorSpace(space);
			return this;
		}

		public Builder quarters(Collection<Quarter> selection) {
			this.quarterCriteria = QuarterCriteria.in(selection);
			return this;
		}

		public RoomFilter build() {
			return new RoomFilter(livingPeriod, roomCriteria, pricingCriteria, new FlatShareCriteria(city,
					quarterCriteria));
		}
	}

	public static Builder builder(LivingPeriod livingPeriod, String city) {
		return new Builder(livingPeriod, city);
	}

	private final LivingPeriod livingPeriod;

	private final RoomCriteria roomCriteria;
	private final PricingCriteria pricingCriteria;
	private final FlatShareCriteria flatShareCriteria;

	public RoomFilter(LivingPeriod livingPeriod, RoomCriteria roomCriteria, PricingCriteria pricingCriteria,
			FlatShareCriteria flatShareCriteria) {
		super();

		this.livingPeriod = livingPeriod;
		this.roomCriteria = roomCriteria;
		this.pricingCriteria = pricingCriteria;
		this.flatShareCriteria = flatShareCriteria;
	}

	@Override
	public boolean apply(RoomOffer offerToMatch) {
		return offerToMatch.getFreePeriod().contains(livingPeriod) && roomCriteria.apply(offerToMatch.getRoom())
				&& pricingCriteria.apply(offerToMatch.getPricing())
				&& flatShareCriteria.apply(offerToMatch.getFlatShare());
	}

}
