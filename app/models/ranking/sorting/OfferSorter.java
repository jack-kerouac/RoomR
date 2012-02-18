package models.ranking.sorting;

import java.util.List;
import java.util.Set;

import models.ranking.matching.ScoredRoomOffer;

/**
 * Implementations of this class are responsible for sorting an unsorted set of
 * already matched room offers for a request into a sequence that is displayed
 * to the seeker.
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public interface OfferSorter {

	public List<ScoredRoomOffer> sort(Set<ScoredRoomOffer> scoredOffers);

}
