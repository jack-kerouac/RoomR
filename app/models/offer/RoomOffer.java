package models.offer;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import play.db.jpa.Model;

import com.google.common.base.Objects;

@Entity
public class RoomOffer extends Model {

	@OneToOne(optional = false)
	public Flatshare flatshare;

	@Embedded
	public RoomDetails roomDetails;

	@Embedded
	public SeekerCriteria criteria;

	public String description;

	public String contactEmail;

	@OneToMany(mappedBy = "roomOffer")
	public Set<RoomOfferApplication> applications;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("flatshareID", (flatshare != null ? flatshare.id : "null")).add("room details", roomDetails)
				.add("seeker criteria", criteria).toString();
	}
}
