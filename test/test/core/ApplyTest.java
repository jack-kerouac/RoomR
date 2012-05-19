package test.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.common.Age;
import models.common.FloorSpace;
import models.common.Gender;
import models.notification.NotificationService;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.SeekerCriteria;
import models.user.RoomrUser;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import test.common.AbstractCoreTest;

import com.google.common.collect.ImmutableSet;

import facade.SeekerFacade;


// FIXME (Flo): reenable this test, as soon as we fixed the issue with the testability of domain classes
@Ignore
public class ApplyTest extends AbstractCoreTest {

	private SeekerFacade seekerFacade;

	private RoomOfferRepository offerRepository;
	private RoomOfferApplicationRepository applicationRepository;
	private NotificationService notificationService;

	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
		offerRepository = injector.getInstance(RoomOfferRepository.class);
		applicationRepository = injector.getInstance(RoomOfferApplicationRepository.class);
		notificationService = injector.getInstance(NotificationService.class);
	}

	@Test
	public void test() {
		RoomOffer offer = new RoomOffer();
		offer.id = 1l;
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = ImmutableSet.of(Gender.male, Gender.female);
		offer.criteria.minAge = new Age(20);
		offer.criteria.maxAge = new Age(30);

		offer.roomDetails = new RoomDetails();
		offer.roomDetails.totalRentPerMonthInEuro = 300.0;
		offer.roomDetails.freeFrom = new Date(2012, 5, 1);
		offer.roomDetails.roomSize = new FloorSpace(40.0);

		RoomrUser applicant = new RoomrUser();
		applicant.gaeUserEmail = "a@b.de";
		applicant.name = "Anton";

		when(offerRepository.find(offer.id)).thenReturn(offer);

		RoomOfferApplication application = seekerFacade.apply(applicant, offer.id, "GIVE. ME. THE. ROOM.");

		verify(applicationRepository).add(application);
		verify(notificationService).notifyFlatshareOfNewApplication(offer, application);
		
		assertEquals(State.WAITING_FOR_INVITATION, application.currentState);
		assertEquals(applicant, application.getApplicant());
		assertEquals("GIVE. ME. THE. ROOM.", application.message);
		assertEquals(offer, application.getRoomOffer());
	}

}
