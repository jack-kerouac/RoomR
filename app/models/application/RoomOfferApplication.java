package models.application;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.db.ebean.Model;

import com.google.common.base.Objects;

@Entity
public class RoomOfferApplication extends Model {

	@Id
	Long id;

	public static enum State {
		// TODO: define useful states
		WAITING_FOR_INVITATION;
	}

	public State currentState;
	public String message;
	public RoomrUser applicant;
	public RoomOffer roomOffer;

	public static Finder<Long, RoomOfferApplication> find = new Finder<Long, RoomOfferApplication>(
			Long.class, RoomOfferApplication.class);

	public static List<RoomOfferApplication> all() {
		return find.all();
	}

	public static void create(RoomOfferApplication roomOfferApplication) {
		roomOfferApplication.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("applicant", applicant).add("roomOffer", roomOffer)
				.add("currentState", currentState).add("message", message)
				.toString();
	}
}