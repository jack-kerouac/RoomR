package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.common.Address;
import models.common.Age;
import models.common.Floor;
import models.common.FloorSpace;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.SeekerCriteria;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;

import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import controllers.formdata.OfferFormData;

@InjectSupport
public class Offers extends AbstractRoomrController {

	@Inject
	private static RoomOfferRepository offerRepository;

	public static void offerForm(OfferFormData formData) {
		if (formData == null) {
			// no offer to prefill form with
			formData = new OfferFormData();
			formData.minAge = 18;
			formData.maxAge = 50;
			formData.genders = Sets.newHashSet(Gender.male, Gender.female);
		}

		Floor[] floors = Floor.values();
		SmokingTolerance[] smokingTolerances = SmokingTolerance.values();
		render(formData, floors, smokingTolerances);
	}

	public static void createOffer(@Valid OfferFormData formData) {
		if (formData.minAge > formData.maxAge) {
			validation.addError("formData.maxAge", "offerFormData.maxAge.error.smallerThanMinAge");
		}

		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(formData);
		}

		RoomOffer offer = new RoomOffer();
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = formData.genders;
		offer.criteria.minAge = new Age(formData.minAge);
		offer.criteria.maxAge = new Age(formData.maxAge);

		offer.flatshare = new Flatshare();
		offer.flatshare.address = new Address(formData.street, formData.streetNumber, formData.zipCode, formData.city);
		offer.flatshare.geoLocation = new GeoPt(formData.lat, formData.lng);
		offer.flatshare.floor = formData.floor;
		offer.flatshare.smokingTolerance = formData.smokingTolerance;

		offer.roomDetails = new RoomDetails();
		offer.roomDetails.roomSize = new FloorSpace(formData.squareMeters);
		offer.roomDetails.totalRentPerMonthInEuro = formData.totalRentPerMonthInEuro;

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
