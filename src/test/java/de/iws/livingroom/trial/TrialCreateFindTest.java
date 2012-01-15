package de.iws.livingroom.trial;

import java.util.Set;

import org.fest.assertions.Assertions;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vercer.engine.persist.ObjectDatastore;

import de.iws.livingroom.application.OfferFacade;
import de.iws.livingroom.application.FacadeModule;
import de.iws.livingroom.domain.Address;
import de.iws.livingroom.domain.Location;
import de.iws.livingroom.domain.Offer;
import de.iws.livingroom.domain.OfferModule;
import de.iws.livingroom.domain.RoomDetails;
import de.iws.livingroom.infrastructure.storage.TwigModule;
import de.iws.livingroom.infrastructure.trials.appengine.twig.TwigModelTest;

public class TrialCreateFindTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testAssembly() {
		Injector injector = Guice.createInjector(new OfferModule(), new FacadeModule(), new TwigModule());
		OfferFacade facade = injector.getInstance(OfferFacade.class);

		Offer offer1 = facade.create(
				new Location("Lehel", new Address("Knöbelstraße", 14, 80538, "München")),
				new RoomDetails(new Interval(DateTime.now(), Duration.standardDays(30)), Money
						.of(CurrencyUnit.EUR, 300)));
		Offer offer2 = facade.create(
				new Location("Lehel", new Address("Knöbelstraße", 12, 80538, "München")),
				new RoomDetails(new Interval(DateTime.now(), Duration.standardDays(60)), Money
						.of(CurrencyUnit.EUR, 400)));

		System.out.println(offer1);
		
		Set<Offer> all = facade.findAll();
		Assertions.assertThat(all).containsOnly(offer1, offer2);
	}
}
