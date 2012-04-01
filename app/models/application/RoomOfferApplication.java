package models.application;

import javax.persistence.Id;

import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import com.google.common.base.Objects;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.NotSaved;

public class RoomOfferApplication extends ObjectifyModel {

	@Id
	Long id;
	
	
	public static enum State {
		// TODO: define useful states
		WAITING_FOR_INVITATION;
	}

	public State currentState;

	public String message;

	public Key<RoomrUser> applicantKey;

	@NotSaved
	public Key<RoomOffer> roomOfferKey;

	
	/**
	 * loads the (cached) applicant for this user from the datastore
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
	 * loads the (cached) applicant for this user from the datastore
	 * 
	 * @return the applicant for this application
	 */
//	public Rooom getApplicant() {
//		if (this.applicantKey == null) {
//			return null;
//		}
//		return Datastore.find(this.applicantKey, false);
//	}

	
	
	
	@Override
	public String toString() {
		String applicant = getApplicant() != null ? getApplicant().gaeUserEmail : "none"; 
		// TODO fetch room offer using the key
		String roomOffer = "dummyRoomOffer";
		return Objects.toStringHelper(this).add("applicant", applicant).add("roomOffer", "dummyRoomOffer")
				.add("currentState", currentState).add("message", message).toString();
	}

}
