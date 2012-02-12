package models.offer;

import models.flatshare.Flatshare;
import play.data.validation.Valid;

import com.google.code.twig.annotation.Child;
import com.google.code.twig.annotation.Id;
import com.google.code.twig.annotation.Independent;
import com.google.common.base.Objects;

public final class Offer {

	@Id
	public Long id;

	@Valid
	@Independent
	public Flatshare flatshare;
	
	@Valid
	@Child
	public RoomDetails roomDetails;

	@Valid
	@Child
	public SeekerCriteria criteria;

	public Offer() {}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("flatshare", flatshare).add("room details", roomDetails).add("seeker criteria", criteria)
				.toString();
	}

}
