package de.iws.livingroom.application.seeking;

import javax.annotation.PostConstruct;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import de.iws.livingroom.domain.common.Age;
import de.iws.livingroom.domain.common.FloorSpace;
import de.iws.livingroom.domain.common.Gender;
import de.iws.livingroom.domain.common.Language;
import de.iws.livingroom.domain.common.LivingPeriod;
import de.iws.livingroom.domain.common.PersonalProfile;
import de.iws.livingroom.domain.market.PublishedRoomOffer;
import de.iws.livingroom.domain.market.PublishedRoomOfferRepository;
import de.iws.livingroom.domain.offering.flatshare.Address;
import de.iws.livingroom.domain.offering.flatshare.Flat;
import de.iws.livingroom.domain.offering.flatshare.FlatShare;
import de.iws.livingroom.domain.offering.flatshare.Floor;
import de.iws.livingroom.domain.offering.flatshare.Quarter;
import de.iws.livingroom.domain.offering.flatshare.Resident;
import de.iws.livingroom.domain.offering.offer.Pricing;
import de.iws.livingroom.domain.offering.offer.Room;
import de.iws.livingroom.domain.offering.offer.RoomOffer;
import de.iws.livingroom.domain.offering.offer.criteria.AgeCriteria;
import de.iws.livingroom.domain.offering.offer.criteria.GenderCriteria;
import de.iws.livingroom.domain.offering.offer.criteria.NewResidentCriteria;
import de.iws.livingroom.domain.seeking.FlatshareSeeker;
import de.iws.livingroom.domain.seeking.FlatshareSeekerRepository;

public class RepositoryFiller {

	private FlatshareSeekerRepository seekerRepos;

	private PublishedRoomOfferRepository offerRepos;

	private static Money m(int amount) {
		return Money.of(CurrencyUnit.EUR, amount);
	}

	@PostConstruct
	public void createSomeData() {
		fillSeekers();

		fillOffers();
	}

	private void fillSeekers() {
		seekerRepos.add(new FlatshareSeeker("alice", new PersonalProfile(Gender.female, new Age(20))));
		seekerRepos.add(new FlatshareSeeker("bob", new PersonalProfile(Gender.male, new Age(30))));
	}

	private void fillOffers() {
		LivingPeriod unlimited = new LivingPeriod(new DateMidnight(2011, 9, 1));
		LivingPeriod limited = new LivingPeriod(new DateMidnight(2011, 10, 1), new DateMidnight(2011, 11, 30));

		RoomOffer limited_500_30m2_mw_20_30 = new RoomOffer(1, new FlatShare(new Flat(new FloorSpace(100),
				Floor.basement, "Altbau", "TV & X-Box", "Flatrate-Telefon", "Badewanne"), new Quarter("Lehel"),
				new Address("Knöbelstraße", "14", 80538, "München", "Edelmann"), ImmutableSet.of(new Resident(
						Gender.male, new DateMidnight(1987, 6, 5), ImmutableSet.<Language> of()))), unlimited,
				new Pricing(m(500), m(0), m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(
						new GenderCriteria(true, true), new AgeCriteria(new Age(20), new Age(30))));
		offerRepos.add(new PublishedRoomOffer(limited_500_30m2_mw_20_30, new DateTime()));

		RoomOffer limited_600_30m2_mw_20_30 = new RoomOffer(2, new FlatShare(new Flat(new FloorSpace(80), Floor.third,
				"Altbau saniert", "Flachbildschirm", "Flatrate-Telefon", "Badewanne"), new Quarter("Lehel"),
				new Address("Im Tal", "15", 80538, "München", "Mustermann"), ImmutableSet.of(new Resident(Gender.male,
						new DateMidnight(1987, 6, 5), ImmutableSet.<Language> of()))), unlimited, new Pricing(m(600),
				m(0), m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(true, true),
				new AgeCriteria(new Age(20), new Age(30))));
		offerRepos.add(new PublishedRoomOffer(limited_600_30m2_mw_20_30, new DateTime()));

		RoomOffer limited_600_25m2_mw_20_30 = new RoomOffer(3, new FlatShare(new Flat(new FloorSpace(50), Floor.fourth,
				"Neubau", "TV", "IP-Telefon", "Badewanne"), new Quarter("Isarvorstadt"), new Address("Fraunhoferstr.",
				"128a", 80683, "München", "Müller"), ImmutableSet.of(new Resident(Gender.female, new DateMidnight(1987,
				6, 5), ImmutableSet.<Language> of()))), limited, new Pricing(m(600), m(0), m(1100)), new Room(
				new FloorSpace(25)), new NewResidentCriteria(new GenderCriteria(true, true), new AgeCriteria(
				new Age(20), new Age(30))));
		offerRepos.add(new PublishedRoomOffer(limited_600_25m2_mw_20_30, new DateTime()));

		RoomOffer limited_600_30m2_m_20_30 = new RoomOffer(4, new FlatShare(new Flat(new FloorSpace(80), Floor.second,
				"Altbau", "TV", "Analagtelefon", "Dusche und Badewanne"), new Quarter("Thalkirchen"), new Address(
				"Implerstr", "60", 80483, "München", "Mayer"), ImmutableSet.of(new Resident(Gender.female,
				new DateMidnight(1983, 6, 5), ImmutableSet.<Language> of()))), limited, new Pricing(m(600), m(0),
				m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(true, false),
				new AgeCriteria(new Age(20), new Age(30))));
		offerRepos.add(new PublishedRoomOffer(limited_600_30m2_m_20_30, new DateTime()));

		RoomOffer limited_600_30m2_w_20_30 = new RoomOffer(5, new FlatShare(new Flat(new FloorSpace(60), Floor.second,
				"Reihenhaus", "TV", "Telefon", "Dusche und Badewanne"), new Quarter("Moosach"), new Address(
				"Landsberger Str.", "90", 80224, "München", "Huber"), ImmutableSet.of(new Resident(Gender.male,
				new DateMidnight(1986, 4, 5), ImmutableSet.<Language> of()))), unlimited, new Pricing(m(600), m(0),
				m(1100)), new Room(new FloorSpace(30)), new NewResidentCriteria(new GenderCriteria(false, true),
				new AgeCriteria(new Age(20), new Age(30))));
		offerRepos.add(new PublishedRoomOffer(limited_600_30m2_w_20_30, new DateTime()));
	}
}
