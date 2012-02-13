package models.matching;

import java.util.List;

import models.common.Age;
import models.common.Gender;
import models.request.RoomRequest;

import com.google.common.base.Optional;

public interface OfferMatcher {

	/**
	 * The returned list of {@link RankedRoomOffer} is sorted by the {@link RankedRoomOffer#matchingScore}.
	 */
	public List<RankedRoomOffer> match(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender);

}
