package test.common;

import java.sql.Date;

import models.common.FloorSpace;
import models.common.Gender;
import models.common.GeoPt;
import models.flatshare.Flatshare;
import models.flatshare.StreetViewParameters;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.SeekerCriteria;
import modules.CoreModule;

import org.junit.Before;

import play.test.BaseTest;
import play.test.Fixtures;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractCoreTest extends BaseTest {

	protected Injector injector;

	private Flatshare dummyFlatshare;
	private RoomOffer dummyRoomOffer;

	@Before
	public final void setupInjector() {
		injector = Guice.createInjector(new CoreModule(),
				new InfrastructureMockModule());

		Fixtures.deleteDatabase();
	}

	protected Flatshare getDummyFlatshare() {
		if (dummyFlatshare != null) {
			return dummyFlatshare;
		}

		dummyFlatshare = new Flatshare();
		dummyFlatshare.geoLocation = new GeoPt(0.0f, 0.0f);
		dummyFlatshare.streetViewParameters = new StreetViewParameters();
		return dummyFlatshare.save();
	}

	protected RoomOffer getDummyOffer() {
		if (dummyRoomOffer != null) {
			return dummyRoomOffer;
		}

		dummyRoomOffer = new RoomOffer();
		dummyRoomOffer.criteria = new SeekerCriteria();
		dummyRoomOffer.criteria.genders = ImmutableSet.of(Gender.male,
				Gender.female);
		dummyRoomOffer.criteria.minAge = 20;
		dummyRoomOffer.criteria.maxAge = 30;

		dummyRoomOffer.roomDetails = new RoomDetails();
		dummyRoomOffer.roomDetails.totalRentPerMonthInEuro = 300.0;
		dummyRoomOffer.roomDetails.freeFrom = new Date(2012, 5, 1);
		dummyRoomOffer.roomDetails.roomSize = new FloorSpace(40.0);

		dummyRoomOffer.flatshare = getDummyFlatshare();

		return dummyRoomOffer.save();
	}

}
