import junit.framework.Assert;
import models.common.Address;
import models.common.Age;
import models.common.Floor;
import models.common.FloorSpace;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.offer.Offer;
import models.offer.RoomDetails;
import models.offer.SeekerCriteria;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ranges;

public class TwigOfferTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	private Offer createInstance() {
		Offer offer = new Offer();
		// this is IMPORTANT! when not setting the ID the childs cannot be persisted and you will receive an
		// IllegalArgumentException: Key specification is incomplete
		offer.id = 1l;
		
		offer.flatshare = new Flatshare();
		offer.flatshare.floor = Floor.second;
		offer.flatshare.smokingTolerance = SmokingTolerance.allowedInRoom;
		offer.flatshare.address = new Address("Knöbelstr.", 14, 80538, "München");

		offer.roomDetails = new RoomDetails(new Interval(new DateTime(), Duration.standardDays(10)), Money.of(
				CurrencyUnit.EUR, 10.0));
		offer.roomDetails.roomSize = new FloorSpace(10);
		
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = ImmutableSet.of(Gender.male);
		offer.criteria.setAgeRange(Ranges.closed(new Age(20), new Age(31)));

		return offer;
	}

	@Test
	public void testPersist() {
		Offer offer = createInstance();

		// create a new light-weight stateful datastore for every request
		ObjectDatastore datastore = new AnnotationObjectDatastore();

		// store the instance and all other reachable instances
		Key key = datastore.store(offer);

		datastore.disassociateAll();

		Offer result = datastore.load(Offer.class, key.getId());
		System.out.println(offer);
		Assert.assertEquals(offer.toString(), result.toString());
	}
}
