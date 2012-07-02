package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import models.application.RoomOfferApplication;
import models.notification.NotificationService;
import models.offer.RoomOffer;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class SeekerFacadeTest extends UnitTest {

	private SeekerFacade facadeUnderTest;
	private NotificationService notificationService;

	@Before
	public void setUp() {
		facadeUnderTest = new SeekerFacade();
		notificationService = mock(NotificationService.class);
		facadeUnderTest.setNotificationService(notificationService);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveRoomOfferApplicationWithInvalidOfferState() {
		// set up mocks
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.DELETED;
		mockedOffer.save();
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = mockedOffer;
		mockedApplication.save();

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication.id);

		// verify
		verifyZeroInteractions(notificationService);
	}

	// @Test(expected = IllegalStateException.class)
	// public void testRemoveRoomOfferApplicationWithInvalidApplicationState() {
	// // set up mocks
	// RoomOffer mockedOffer = new RoomOffer();
	// Long mockedOfferApplicationId = 32L;
	// mockedOffer.currentState = RoomOffer.State.PUBLIC;
	// RoomOfferApplication application = new RoomOfferApplication();
	// RoomOfferApplication spyedApplication = spy(application);
	// spyedApplication.currentState = RoomOfferApplication.State.ACCEPTED;
	// when(roomOfferApplicationRepository.findApplication(mockedOfferApplicationId)).thenReturn(spyedApplication);
	// when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);
	//
	// // trigger method
	// facadeUnderTest.removeRoomOfferApplication(mockedOfferApplicationId);
	//
	// // verify
	// verifyZeroInteractions(notificationService);
	// verifyZeroInteractions(roomOfferApplicationRepository);
	// }
	//
	// @Test
	// public void testRemoveRoomOfferApplicationAlreadyInvited() {
	// // set up mocks
	// RoomOffer mockedOffer = new RoomOffer();
	// Long mockedOfferApplicationId = 32L;
	// mockedOffer.currentState = RoomOffer.State.PUBLIC;
	// RoomOfferApplication application = new RoomOfferApplication();
	// RoomOfferApplication spyedApplication = spy(application);
	// spyedApplication.currentState = RoomOfferApplication.State.INVITED;
	// when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);
	// when(roomOfferApplicationRepository.findApplication(mockedOfferApplicationId)).thenReturn(spyedApplication);
	//
	// // trigger method
	// facadeUnderTest.removeRoomOfferApplication(mockedOfferApplicationId);
	//
	// // verify
	// verify(notificationService).notifyFlatshareOfRemovedApplication(mockedOffer,
	// spyedApplication);
	// verify(roomOfferApplicationRepository).remove(spyedApplication);
	// }
	//
	// @Test
	// public void testRemoveRoomOfferApplicationNotInvited() {
	// // set up mocks
	// RoomOffer mockedOffer = new RoomOffer();
	// mockedOffer.currentState = RoomOffer.State.PUBLIC;
	// Long mockedOfferApplicationId = 32L;
	// RoomOfferApplication application = new RoomOfferApplication();
	// RoomOfferApplication spyedApplication = spy(application);
	// spyedApplication.currentState = RoomOfferApplication.State.INVITED;
	// when(spyedApplication.getRoomOffer()).thenReturn(mockedOffer);
	// when(roomOfferApplicationRepository.findApplication(mockedOfferApplicationId)).thenReturn(spyedApplication);
	//
	// // trigger method
	// facadeUnderTest.removeRoomOfferApplication(mockedOfferApplicationId);
	//
	// // verify
	// verify(notificationService).notifyFlatshareOfRemovedApplication(mockedOffer,
	// spyedApplication);
	// verify(roomOfferApplicationRepository).remove(spyedApplication);
	// }
}
