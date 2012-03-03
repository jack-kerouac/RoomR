package models.offer;

import models.flatshare.Flatshare;
import play.data.validation.Valid;

import com.google.code.twig.annotation.Child;
import com.google.code.twig.annotation.Id;
import com.google.code.twig.annotation.Independent;
import com.google.common.base.Objects;

public final class RoomOffer {

	@Id
	public Long id;

	@Valid
	@Independent
	public Flatshare flatshare;
	
	@Valid
	@Child
	public RoomDetails roomDetails;

	// a question that the flatshare can ask to applicants
	// TODO: make question(s) configurable on UI
	public String question = "Darth Vader oder Prinzessin Lea?";
	
	@Valid
	@Child
	public SeekerCriteria criteria;

	public RoomOffer() {}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("flatshare", flatshare).add("room details", roomDetails).add("seeker criteria", criteria)
				.toString();
	}

}
