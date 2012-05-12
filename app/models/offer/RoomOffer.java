package models.offer;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import models.flatshare.Flatshare;
import play.db.ebean.Model;

import com.google.common.base.Objects;

@Entity
public final class RoomOffer extends Model {

	@Id
	public Long id;

	public Flatshare flatshare;

	@Embedded
	public RoomDetails roomDetails;

	@Embedded
	public SeekerCriteria criteria;

	public String description;

	public String contactEmail;

	public static Finder<Long, RoomOffer> find = new Finder<Long, RoomOffer>(
			Long.class, RoomOffer.class);

	public static List<RoomOffer> all() {
		return find.all();
	}

	public static void create(RoomOffer roomOffer) {
		roomOffer.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("flatshare", flatshare).add("room details", roomDetails)
				.add("seeker criteria", criteria).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomOffer other = (RoomOffer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
