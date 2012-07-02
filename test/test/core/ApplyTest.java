package test.core;

import static org.mockito.Mockito.verify;
import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import org.junit.Before;
import org.junit.Test;

import support.FlatshareBuilder;
import support.RoomOfferBuilder;
import test.common.AbstractCoreTest;
import facade.SeekerFacade;

//@Ignore
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
		RoomOffer offer = RoomOfferBuilder.inFlatshare(FlatshareBuilder.validFlatshare().build()).build();

		RoomrUser applicant = new RoomrUser();
		applicant.email = "a@b.de";
		applicant.name = "Anton";
		applicant = applicant.save();

		RoomOfferApplication application = seekerFacade.apply(applicant, offer.id, "GIVE. ME. THE. ROOM.");

		verify(notificationService).notifyFlatshareOfNewApplication(offer, application);

		assertNotNull(RoomOfferApplication.findById(application.id));
		assertEquals(State.WAITING_FOR_INVITATION, application.currentState);
		assertEquals(applicant, application.applicant);
		// domain classes
		// assertEquals(applicant, application.getApplicant());
		assertEquals("GIVE. ME. THE. ROOM.", application.message);
		assertEquals(offer, application.roomOffer);
		// domain classes
		// assertEquals(offer, application.getRoomOffer());
	}
}
