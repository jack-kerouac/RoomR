package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.notification.NotificationService;
import models.offer.RoomOffer;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class SeekerFacadeTest extends UnitTest {

	private SeekerFacade facadeUnderTest;
	private RoomOfferApplicationRepository roomOfferApplicationRepository;
	private NotificationService notificationService;

	@Before
	public void setUp() {
		facadeUnderTest = new SeekerFacade();
		roomOfferApplicationRepository = mock(RoomOfferApplicationRepository.class);
		facadeUnderTest.setRoomOfferApplicationRepository(roomOfferApplicationRepository);
		notificationService = mock(NotificationService.class);
		facadeUnderTest.setNotificationService(notificationService);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveRoomOfferApplicationWithInvalidOfferState() {
		// set up mocks
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.DELETED;
		RoomOfferApplication mockedApplication = mock(RoomOfferApplication.class);
		when(mockedApplication.getRoomOffer()).thenReturn(mockedOffer);

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication);

		// verify
		verifyZeroInteractions(notificationService);
		verifyZeroInteractions(roomOfferApplicationRepository);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveRoomOfferApplicationWithInvalidApplicationState() {
		// set up mocks
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		RoomOfferApplication application = new RoomOfferApplication();
		RoomOfferApplication spyedApplication = spy(application);
		spyedApplication.currentState = RoomOfferApplication.State.ACCEPTED;
		when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(spyedApplication);

		// verify
		verifyZeroInteractions(notificationService);
		verifyZeroInteractions(roomOfferApplicationRepository);
	}

	@Test
	public void testRemoveRoomOfferApplicationAlreadyInvited() {
		// set up mocks
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		RoomOfferApplication application = new RoomOfferApplication();
		RoomOfferApplication spyedApplication = spy(application);
		spyedApplication.currentState = RoomOfferApplication.State.INVITED;
		when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(spyedApplication);

		// verify
		verify(notificationService).notifyFlatshareOfRemovedApplication(mockedOffer, spyedApplication);
		verify(roomOfferApplicationRepository).remove(spyedApplication);
	}

	@Test
	public void testRemoveRoomOfferApplicationNotInvited() {
		// set up mocks
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		RoomOfferApplication application = new RoomOfferApplication();
		RoomOfferApplication spyedApplication = spy(application);
		spyedApplication.currentState = RoomOfferApplication.State.INVITED;
		when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(spyedApplication);

		// verify
		verify(notificationService).notifyFlatshareOfRemovedApplication(mockedOffer, spyedApplication);
		verify(roomOfferApplicationRepository).remove(spyedApplication);
	}
}
