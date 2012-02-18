package models.ranking.matching.inmemory;

import models.offer.RoomOffer;
import models.ranking.matching.ScoredRoomOffer;

import com.google.common.base.Function;

public interface OfferScoringFunction extends Function<RoomOffer, ScoredRoomOffer> {

}
