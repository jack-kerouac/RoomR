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
	public Key<RoomOffer> roomOfferKey;
	
	
	/**
	 * loads the (cached) applicant for this application from the datastore
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


	/**
	 * sets the given roomr user as applicant
	 * @param applicant the applicant for this room offer
	 */
	public void setApplicant(RoomrUser applicant){
		this.applicantKey = new Key<RoomrUser>(RoomrUser.class, applicant.gaeUserEmail);
	}
	
	
	/**
	 * sets the room offer for this application
	 * @param applicant the applicant for this room offer
	 */
	public void setRoomOffer(RoomOffer roomOffer){
		Key<RoomOffer> keyOfNewRoomOffer;
		if (roomOffer.id == null) {
			// roomOffer has to be persisted first to obtain a valid key
			keyOfNewRoomOffer = Datastore.put(roomOffer);
		} else {
			keyOfNewRoomOffer = new Key<RoomOffer>(RoomOffer.class, roomOffer.id);
		}
		this.roomOfferKey = keyOfNewRoomOffer;
	}

	
	/**
	 * loads the (cached) room offer for this application from the datastore
	 * 
	 * @return the applicant for this application
	 */
	public RoomOffer getRoomOffer(){
		if(this.roomOfferKey == null){
			return null;
		}
		try {
			return ObjectifyService.begin().get(this.roomOfferKey);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		String applicant = getApplicant() != null ? getApplicant().gaeUserEmail : "none"; 
		String roomOffer = getRoomOffer() != null ? getRoomOffer().toString() : "none";
		return Objects.toStringHelper(this).add("id",id).add("applicant", applicant).add("roomOffer", roomOffer)
				.add("currentState", currentState).add("message", message).toString();
	}
}