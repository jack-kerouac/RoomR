package test.core;

import static org.mockito.Mockito.verify;
import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import org.junit.Before;
import org.junit.Test;

import test.common.AbstractCoreTest;
import facade.SeekerFacade;

// FIXME (Flo): reenable this test, as soon as we fixed the issue with the testability of domain classes
public class ApplyTest extends AbstractCoreTest {

	private SeekerFacade seekerFacade;

	private NotificationService notificationService;

	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
		notificationService = injector.getInstance(NotificationService.class);
	}

	@Test
	public void test() {
		RoomOffer offer = getDummyOffer();

		RoomrUser applicant = new RoomrUser();
		applicant.email = "a@b.de";
		applicant.name = "Anton";
		applicant = applicant.save();

		RoomOfferApplication application = seekerFacade.apply(applicant,
				offer.id, "GIVE. ME. THE. ROOM.");

		verify(notificationService).notifyFlatshareOfNewApplication(offer,
				application);

		assertNotNull(RoomOfferApplication.findById(application.id));
		assertEquals(State.WAITING_FOR_INVITATION, application.currentState);
		assertEquals(applicant, application.applicant);
		assertEquals("GIVE. ME. THE. ROOM.", application.message);
		assertEquals(offer, application.roomOffer);
	}
}
