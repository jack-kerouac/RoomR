import java.util.Date;

import junit.framework.Assert;
import models.Address;
import models.Location;
import models.Offer;
import models.RoomDetails;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;

public class TwigOfferTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	private Offer createInstance() {
		Location location = new Location("Lehel", new Address("Knöbelstr.", 14,
				80538, "München"));
		RoomDetails roomDetails = new RoomDetails(new Interval(new DateTime(),
				Duration.standardDays(10)), Money.of(CurrencyUnit.EUR, 10.0));

		Offer offer = new Offer(location, roomDetails);
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
		Assert.assertEquals(offer.toString(), result.toString());
		Assert.assertEquals(offer.getLocation().toString(), result.getLocation().toString());
	}
}
