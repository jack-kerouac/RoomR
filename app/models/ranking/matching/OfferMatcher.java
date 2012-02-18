package models.ranking.matching;

import java.util.Set;

import models.common.Age;
import models.common.Gender;
import models.request.RoomRequest;

import com.google.common.base.Optional;

/**
 * Implementations of this interface are responsible for querying stored room
 * offers and matching them with the request and additional attributes provided
 * by the seeker. The matched room offers are returned in a Set.
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public interface OfferMatcher {

	public Set<ScoredRoomOffer> match(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender);

}
