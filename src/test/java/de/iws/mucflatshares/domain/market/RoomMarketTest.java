package de.iws.mucflatshares.domain.market;

import static org.junit.Assert.assertTrue;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.ImmutableSet;

import de.iws.mucflatshares.domain.common.Age;
import de.iws.mucflatshares.domain.common.FloorSpace;
import de.iws.mucflatshares.domain.common.Gender;
import de.iws.mucflatshares.domain.common.Language;
import de.iws.mucflatshares.domain.common.LivingPeriod;
import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.internal.repos.impl.inmemory.InMemoryPublishedRoomOfferRepository;
import de.iws.mucflatshares.domain.offering.flatshare.Address;
import de.iws.mucflatshares.domain.offering.flatshare.Flat;
import de.iws.mucflatshares.domain.offering.flatshare.FlatShare;
import de.iws.mucflatshares.domain.offering.flatshare.Floor;
import de.iws.mucflatshares.domain.offering.flatshare.Quarter;
import de.iws.mucflatshares.domain.offering.flatshare.Resident;
import de.iws.mucflatshares.domain.offering.offer.Pricing;
import de.iws.mucflatshares.domain.offering.offer.Room;
import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.offering.offer.criteria.AgeCriteria;
import de.iws.mucflatshares.domain.offering.offer.criteria.GenderCriteria;
import de.iws.mucflatshares.domain.offering.offer.criteria.NewResidentCriteria;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;
import de.iws.mucflatshares.domain.seeking.filter.criterias.PricingCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.RoomCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare.FlatShareCriteria;
import de.iws.mucflatshares.domain.seeking.filter.criterias.flatshare.QuarterCriteria;

public class RoomMarketTest {

	private RoomMarket market = new RoomMarket();
	private LivingPeriod limited;
	private LivingPeriod unlimited;

	private static Money m(int amount) {
		return Money.of(CurrencyUnit.EUR, amount);
	}

	@Before
	public void addOffers() {
		ReflectionTestUtils.setField(market, "offerRepos", new InMemoryPublishedRoomOfferRepository());

		unlimited = new LivingPeriod(new DateMidnight(2011, 9, 1));
		limited = new LivingPeriod(new DateMidnight(2011, 10, 1), new DateMidnight(2011, 11, 30));

		RoomOffer limited_500_30m2_mw_20_30 = new RoomOffer(0, new FlatShare(new Flat(new FloorSpace(100),
				Floor.basement, "Altbau", "TV & X-Box", "Flatrate-Telefon", "Badewanne"), new Quarter("Lehel"),
				new Address("Knöbelstraße", "14", 80538, "München", "Edelmann"), ImmutableSet.of(new Resident(
						Gender.male, new DateMidnight(1987, 6, 5), ImmutableSet.<Language> of()))), unlimited,
				new Pricing(m(500), m(0), m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(
						new GenderCriteria(true, true), new AgeCriteria(new Age(20), new Age(30))));
		market.offerRoom(limited_500_30m2_mw_20_30);

		RoomOffer limited_600_30m2_mw_20_30 = new RoomOffer(1, new FlatShare(new Flat(new FloorSpace(80), Floor.third,
				"Altbau saniert", "Flachbildschirm", "Flatrate-Telefon", "Badewanne"), new Quarter("Lehel"),
				new Address("Im Tal", "15", 80538, "München", "Mustermann"), ImmutableSet.of(new Resident(Gender.male,
						new DateMidnight(1987, 6, 5), ImmutableSet.<Language> of()))), unlimited, new Pricing(m(600),
				m(0), m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(true, true),
				new AgeCriteria(new Age(20), new Age(30))));
		market.offerRoom(limited_600_30m2_mw_20_30);

		RoomOffer limited_600_25m2_mw_20_30 = new RoomOffer(2, new FlatShare(new Flat(new FloorSpace(50), Floor.fourth,
				"Neubau", "TV", "IP-Telefon", "Badewanne"), new Quarter("Isarvorstadt"), new Address("Fraunhoferstr.",
				"128a", 80683, "München", "Müller"), ImmutableSet.of(new Resident(Gender.female, new DateMidnight(1987,
				6, 5), ImmutableSet.<Language> of()))), limited, new Pricing(m(600), m(0), m(1100)), new Room(
				new FloorSpace(25)), new NewResidentCriteria(new GenderCriteria(true, true), new AgeCriteria(
				new Age(20), new Age(30))));
		market.offerRoom(limited_600_25m2_mw_20_30);

		RoomOffer limited_600_30m2_m_20_30 = new RoomOffer(3, new FlatShare(new Flat(new FloorSpace(80), Floor.second,
				"Altbau", "TV", "Analagtelefon", "Dusche und Badewanne"), new Quarter("Thalkirchen"), new Address(
				"Implerstr", "60", 80483, "München", "Mayer"), ImmutableSet.of(new Resident(Gender.female,
				new DateMidnight(1983, 6, 5), ImmutableSet.<Language> of()))), limited, new Pricing(m(600), m(0),
				m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(true, false),
				new AgeCriteria(new Age(20), new Age(30))));
		market.offerRoom(limited_600_30m2_m_20_30);

		RoomOffer limited_600_30m2_w_20_30 = new RoomOffer(4, new FlatShare(new Flat(new FloorSpace(60), Floor.second,
				"Reihenhaus", "TV", "Telefon", "Dusche und Badewanne"), new Quarter("Moosach"), new Address(
				"Landsberger Str.", "90", 80224, "München", "Huber"), ImmutableSet.of(new Resident(Gender.male,
				new DateMidnight(1986, 4, 5), ImmutableSet.<Language> of()))), unlimited, new Pricing(m(600), m(0),
				m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(false, true),
				new AgeCriteria(new Age(20), new Age(30))));
		market.offerRoom(limited_600_30m2_w_20_30);
	}

	@Test
	public void testRetrieveCurrentOffers() {
		assertTrue(market.retrieveCurrentOffers(
				new RoomFilter(limited, RoomCriteria.minFloorSpace(new FloorSpace(26)),
						PricingCriteria.limitedMonthlyTotal(m(550)), new FlatShareCriteria("München",
								QuarterCriteria.all())), new PersonalProfile(Gender.male, new Age(25))).size() == 1);
	}
}
