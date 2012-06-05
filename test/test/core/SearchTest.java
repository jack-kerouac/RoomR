package test.core;

import java.sql.Date;
import java.util.List;

import models.common.FloorSpace;
import models.common.Gender;
import models.common.Score;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
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

	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
	}

	@Test
	public void testOneOffer() {
		RoomOffer offer = new RoomOffer();
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = ImmutableSet.of(Gender.male, Gender.female);
		offer.criteria.minAge = 20;
		offer.criteria.maxAge = 30;

		offer.roomDetails = new RoomDetails();
		offer.roomDetails.totalRentPerMonthInEuro = 300.0;
		offer.roomDetails.freeFrom = new Date(2012, 5, 1);
		offer.roomDetails.roomSize = new FloorSpace(40.0);

		offer.flatshare = getDummyFlatshare();

		offer = offer.save();

		RoomRequest request = new RoomRequest();
		request.city = "München";
		request.minRoomSize = new FloorSpace(20.0);
		request.maxRentPerMonthInEuro = 400.0;
		request.startDateQuery = DateQuery.fixedDate(new Date(2012, 5, 15));

		List<ScoredRoomOffer> foundOffer = seekerFacade.search(request,
				Optional.of(24), Optional.of(Gender.male));

		assertEquals(Score.MAX,
				Iterables.getOnlyElement(foundOffer).matchingScore);
		assertTrue(Iterables.getOnlyElement(foundOffer).metCriteria
				.containsAll(ImmutableSet.of(MatchingCriterion.AGE,
						MatchingCriterion.GENDER,
						MatchingCriterion.RENT_PER_MONTH,
						MatchingCriterion.ROOM_SIZE,
						MatchingCriterion.START_DATE)));
	}

}
