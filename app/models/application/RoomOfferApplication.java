package models.application;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.db.jpa.Model;

import com.google.common.base.Objects;

@Entity
public class RoomOfferApplication extends Model {

	public static enum State {
		WAITING_FOR_INVITATION, INVITED, REJECTED, VISITED, ACCEPTED
	}

	public State currentState;
	public String message;

	@ManyToOne
	public RoomrUser applicant;

	@ManyToOne
	public RoomOffer roomOffer;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("applicantID", applicant.id)
				.add("roomOfferID", roomOffer.id)
				.add("currentState", currentState).add("message", message)
				.toString();
	}
}