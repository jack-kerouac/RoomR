package test.core;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;

import org.junit.Before;
import org.junit.Test;

import support.RoomOfferBuilder;
import test.common.AbstractCoreTest;
import facade.SeekerFacade;


//@Ignore
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
		RoomOffer offer = RoomOfferBuilder.withId(1l).build();

		RoomrUser applicant = new RoomrUser();
		applicant.gaeUserEmail = "a@b.de";
		applicant.name = "Anton";

		when(offerRepository.find(offer.id)).thenReturn(offer);

		RoomOfferApplication application = seekerFacade.apply(applicant, offer.id, "GIVE. ME. THE. ROOM.");

		verify(applicationRepository).add(application);
		verify(notificationService).notifyFlatshareOfNewApplication(offer, application);

		assertEquals(State.WAITING_FOR_INVITATION, application.currentState);
		// FIXME (Flo): reenable this assert, as soon as we fixed the issue with the testability of
		// domain classes
		// assertEquals(applicant, application.getApplicant());
		assertEquals("GIVE. ME. THE. ROOM.", application.message);
		// FIXME (Flo): reenable this assert, as soon as we fixed the issue with the testability of
		// domain classes
		// assertEquals(offer, application.getRoomOffer());
	}

}
