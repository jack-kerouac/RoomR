package test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;

import models.common.Age;
import models.common.FloorSpace;
import models.common.Gender;
import models.common.Score;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.SeekerCriteria;
import models.ranking.matching.MatchingCriterion;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.request.RoomRequest.DateQuery;

import org.junit.Before;
import org.junit.Test;

import test.common.AbstractCoreTest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import facade.SeekerFacade;

public class SearchTest extends AbstractCoreTest {

	private SeekerFacade seekerFacade;
	private RoomOfferRepository offerRepository;

	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
		offerRepository = injector.getInstance(RoomOfferRepository.class);
	}

	@Test
	public void testOneOffer() {
		RoomOffer offer = new RoomOffer();
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = ImmutableSet.of(Gender.male, Gender.female);
		offer.criteria.minAge = new Age(20);
		offer.criteria.maxAge = new Age(30);

		offer.roomDetails = new RoomDetails();
		offer.roomDetails.totalRentPerMonthInEuro = 300.0;
		offer.roomDetails.freeFrom = new Date(2012, 5, 1);
		offer.roomDetails.roomSize = new FloorSpace(40.0);

		RoomRequest request = new RoomRequest();
		request.city = "München";
		request.minRoomSize = new FloorSpace(20.0);
		request.maxRentPerMonthInEuro = 400.0;
		request.startDateQuery = DateQuery.fixedDate(new Date(2012, 5, 15));

		when(offerRepository.findAll()).thenReturn(ImmutableSet.of(offer));

		List<ScoredRoomOffer> foundOffer = seekerFacade.search(request, Optional.of(new Age(24)),
				Optional.of(Gender.male));

		assertEquals(Score.MAX, Iterables.getOnlyElement(foundOffer).matchingScore);
		assertTrue(Iterables.getOnlyElement(foundOffer).metCriteria.containsAll(ImmutableSet.of(MatchingCriterion.AGE,
				MatchingCriterion.GENDER, MatchingCriterion.RENT_PER_MONTH, MatchingCriterion.ROOM_SIZE,
				MatchingCriterion.START_DATE)));
	}

}
