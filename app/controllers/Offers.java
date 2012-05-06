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
import models.flatshare.StreetViewParameters;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.SeekerCriteria;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;

import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import controllers.formdata.OfferFormData;
import facade.AdministrationFacade;
import facade.ResidentFacade;
import facade.SeekerFacade;

@InjectSupport
public class Offers extends AbstractRoomrController {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static ResidentFacade residentFacade;

	@Inject
	private static AdministrationFacade administrationFacade;

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
		if (formData.genders.isEmpty()) {
			validation.addError("formData.genders", "validation.offerFormData.gendersEmpty");
		}

		// min/max age being null is handled by @Required annotations
		if (formData.minAge != null && formData.maxAge != null) {
			if (formData.minAge > formData.maxAge) {
				validation.addError("formData.maxAge", "validation.offerFormData.maxAgeSmallerThanMinAge");
			}
		}

		// free from being null is handled by @Required annotations
		if (formData.freeFrom != null && formData.freeTo != null && formData.freeFrom.after(formData.freeTo)) {
			validation.addError("formData.freeTo", "validation.offerFormData.freeToBeforeFreeFrom");
		}

		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(formData);
		}

		RoomOffer offer = new RoomOffer();

		// CRITERIA
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = formData.genders;
		offer.criteria.minAge = new Age(formData.minAge);
		offer.criteria.maxAge = new Age(formData.maxAge);

		// FLATSHARE
		Flatshare flatshare = new Flatshare();
		flatshare.address = new Address(formData.street, formData.streetNumber, formData.zipCode, formData.city);
		flatshare.geoLocation = new GeoPt(formData.lat, formData.lng);

		flatshare.streetViewParameters = new StreetViewParameters();
		flatshare.streetViewParameters.displayStreetView = formData.displayStreetView;
		flatshare.streetViewParameters.streetViewGeoLocation = new GeoPt(formData.streetViewLat, formData.streetViewLng);
		flatshare.streetViewParameters.streetViewHeading = formData.streetViewHeading;
		flatshare.streetViewParameters.streetViewPitch = formData.streetViewPitch;
		flatshare.streetViewParameters.streetViewZoom = formData.streetViewZoom;

		flatshare.floor = formData.floor;
		flatshare.smokingTolerance = formData.smokingTolerance;
		// TODO FR: when to do this?
		// offer.flatshare = flatshare;

		// ROOM DETAILS
		offer.roomDetails = new RoomDetails();
		offer.roomDetails.roomSize = new FloorSpace(formData.squareMeters);
		offer.roomDetails.totalRentPerMonthInEuro = formData.totalRentPerMonthInEuro;
		offer.roomDetails.freeFrom = formData.freeFrom;
		offer.roomDetails.freeTo = formData.freeTo;

		// CONTACT DATA
		// TODO: email

		residentFacade.createFlatshareAndOffer(flatshare, offer);

		viewOffer(offer.id);
	}

	public static void viewAll() {
		ArrayList<RoomOffer> offers = Lists.newArrayList(administrationFacade.findAllOffers());
		render(offers);
	}

	public static void viewOffer(long id) {
		RoomOffer offer = seekerFacade.findOffer(id);
		render(offer);
	}

}
