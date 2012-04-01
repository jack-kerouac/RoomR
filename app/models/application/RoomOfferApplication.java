package models.application;

import javax.persistence.Id;

import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;
import play.modules.objectify.ObjectifyService;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Objects;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.NotSaved;

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

	@NotSaved
	public Key<RoomOffer> roomOfferKey;

	
	/**
	 * sets the given roomr user as applicant
	 * @param applicant the applicant for this room offer
	 */
	public void setApplicant(RoomrUser applicant){
		this.applicantKey = new Key<RoomrUser>(RoomrUser.class, applicant.gaeUserEmail);
	}
	
	/**
	 * loads the (cached) applicant for this user from the datastore
	 * 
	 * @return the applicant for this application
	 */
	public RoomrUser getApplicant(){
		try {
			return ObjectifyService.begin().get(this.applicantKey);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}


	public RoomOffer getRoomOffer() {
		return Datastore.find(this.roomOfferKey, false);
	}

	
	
	
	@Override
	public String toString() {
		String applicant = getApplicant() != null ? getApplicant().gaeUserEmail : "none"; 
		// TODO fetch room offer using the key
		String roomOffer = "dummyRoomOffer";
		return Objects.toStringHelper(this).add("id",id).add("applicant", applicant).add("roomOffer", "dummyRoomOffer")
				.add("currentState", currentState).add("message", message).toString();
	}

}
