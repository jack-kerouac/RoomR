package models.user.seeker;

import java.util.LinkedHashSet;
import java.util.Set;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;

import com.google.code.twig.annotation.Embedded;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Embedded
public class SeekerProfile {

	public Set<RoomOfferApplication> applications = new LinkedHashSet<RoomOfferApplication>();

	private String dummy = "REMOVE ME AGAIN";
	
	public boolean appliedFor(final RoomOffer roomOffer) {
		return Iterables.any(applications, new Predicate<RoomOfferApplication>() {
			@Override
			public boolean apply(RoomOfferApplication application) {
				return application.roomOffer.equals(roomOffer);
			}
		});
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("applications", applications).toString();
	}
}
