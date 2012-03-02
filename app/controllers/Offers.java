package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.common.Age;
import models.common.Floor;
import models.common.Gender;
import models.flatshare.SmokingTolerance;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.SeekerCriteria;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;

import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@InjectSupport
public class Offers extends AbstractRoomrController {

	@Inject
	private static RoomOfferRepository offerRepository;

	public static void offerForm(RoomOffer offer) {
		if (offer == null) {
			// no offer to prefill form with
			offer = new RoomOffer();
			offer.criteria = new SeekerCriteria();
			offer.criteria.minAge = new Age(18);
			offer.criteria.maxAge = new Age(50);
			offer.criteria.genders = Sets.newHashSet(Gender.male, Gender.female);
		}
		
		Floor[] floors = Floor.values();
		SmokingTolerance[] smokingTolerances = SmokingTolerance.values();
		render(offer, floors, smokingTolerances);
	}

	public static void createOffer(@Valid RoomOffer offer, Float lat, Float lng) {
		validation.range(lat, -90.0, 90.0);
		validation.range(lng, -180.0, 180.0);
		
		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(offer);
		}
		
		offer.flatshare.geoLocation = new GeoPt(lat, lng);
		offerRepository.add(offer);

		viewOffer(offer.id);
	}

	public static void viewAll() {
		ArrayList<RoomOffer> offers = Lists.newArrayList(offerRepository.findAll());
		render(offers);
	}

	public static void viewOffer(long id) {
		RoomOffer offer = offerRepository.find(id);
		render(offer);
	}

}
