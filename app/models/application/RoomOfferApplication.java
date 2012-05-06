package models.application;

import javax.persistence.Id;

import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class RoomOfferApplication extends ObjectifyModel {

	@Id
	Long id;

	public static enum State {
		// TODO: define useful states
		WAITING_FOR_INVITATION;
	}

	public State currentState;
	public String message;
	private Key<RoomrUser> applicantKey;
	private Key<RoomOffer> roomOfferKey;

	/**
	 * loads the (cached) applicant for this application from the datastore
	 * 
	 * @return the applicant for this application
	 */
	public RoomrUser getApplicant() {
		if (this.applicantKey == null) {
			return null;
		}
		return Datastore.find(this.applicantKey, false);
	}

	/**
	 * sets the given RoomR user as applicant
	 * 
	 * @param applicant
	 *            the applicant for this room offer
	 */
	public void setApplicant(RoomrUser applicant) {
		Preconditions.checkState(applicant.gaeUserEmail != null, "gae user email has to be set for the applicant");
		this.applicantKey = new Key<RoomrUser>(RoomrUser.class, applicant.gaeUserEmail);
	}

	/**
	 * sets the room offer for this application
	 * 
	 * @param applicant
	 *            the applicant for this room offer
	 */
	public void setRoomOffer(RoomOffer roomOffer) {
		Preconditions.checkState(roomOffer.id != null, "the id of the room offer has to be set for the applicant");
		Key<RoomOffer> keyOfNewRoomOffer = new Key<RoomOffer>(RoomOffer.class, roomOffer.id);
		this.roomOfferKey = keyOfNewRoomOffer;
	}

	/**
	 * loads the (cached) room offer for this application from the datastore
	 * 
	 * @return the applicant for this application
	 */
	public RoomOffer getRoomOffer() {
		if (this.roomOfferKey == null) {
			return null;
		}
		return Datastore.find(this.roomOfferKey, false);
	}

	@Override
	public String toString() {
		String applicant = getApplicant() != null ? getApplicant().gaeUserEmail : "none";
		String roomOffer = getRoomOffer() != null ? getRoomOffer().toString() : "none";
		return Objects.toStringHelper(this).add("id", id).add("applicant", applicant).add("roomOffer", roomOffer)
				.add("currentState", currentState).add("message", message).toString();
	}
}