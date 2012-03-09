package models.application;

import models.offer.RoomOffer;
import models.user.RoomrUser;

import com.google.common.base.Objects;

public class RoomOfferApplication {

	public static enum State {
		// TODO: define useful states
		WAITING_FOR_INVITATION;
	}

	public State currentState;

	public String message;

	public RoomrUser applicant;

	public RoomOffer roomOffer;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("applicant", applicant.gaeUser.getEmail()).add("roomOffer", roomOffer.id)
				.add("currentState", currentState).add("message", message).toString();
	}

}
