package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.common.Address;
import models.common.Floor;
import models.common.FloorSpace;
import models.common.Gender;
import models.common.GeoPt;
import models.flatshare.AdditionalSpace;
import models.flatshare.Appliance;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.flatshare.StreetViewParameters;
import models.flatshare.TypeOfHouse;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.RoomOfferTokenService;
import models.offer.SeekerCriteria;
import models.user.RoomrUser;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import controllers.formdata.OfferFormData;
import facade.AdministrationFacade;
import facade.ResidentFacade;
import facade.SeekerFacade;
import facade.UserFacade;
import facade.exception.RoomOfferUpdateException;

@InjectSupport
public class Offers extends AbstractRoomrController {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static UserFacade userFacade;

	@Inject
	private static ResidentFacade residentFacade;

	@Inject
	private static AdministrationFacade administrationFacade;

	@Inject
	private static RoomOfferTokenService tokenService;

	public static void createOfferForm(OfferFormData formData) {
		if (formData == null) {
			// no offer to prefill form with
			formData = new OfferFormData();
			formData.minAge = 18;
			formData.maxAge = 50;
			formData.genders = Sets.newHashSet(Gender.male, Gender.female);
			formData.appliances = ImmutableSet.of(Appliance.clothesWasher,
					Appliance.dishWasher);
			Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
			if (loggedInUser.isPresent()) {
				formData.email = userFacade.getLoggedInUser().get().email;
			}
		}

		renderOfferForm(formData);
	}

	public static void createOffer(@Valid OfferFormData formData) {
		if (formData.genders.isEmpty()) {
			validation.addError("formData.genders",
					"validation.offerFormData.gendersEmpty");
		}

		// min/max age being null is handled by @Required annotations
		if (formData.minAge != null && formData.maxAge != null) {
			if (formData.minAge > formData.maxAge) {
				validation.addError("formData.maxAge",
						"validation.offerFormData.maxAgeSmallerThanMinAge");
			}
		}

		// free from being null is handled by @Required annotations
		if (formData.freeFrom != null && formData.freeTo != null
				&& formData.freeFrom.after(formData.freeTo)) {
			validation.addError("formData.freeTo",
					"validation.offerFormData.freeToBeforeFreeFrom");
		}

		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.createOfferForm(formData);
		}

		RoomOffer offer = new RoomOffer();
		Flatshare flatshare = new Flatshare();

		copy(formData, flatshare, offer);

		residentFacade.createFlatshareAndOffer(flatshare, offer);

		viewOffer(offer.id);
	}

	public static void editOfferForm(
			@Required(message = "room offer ID required") Long id,
			@Required(message = "authentication token required") String authToken) {
		if (validation.hasErrors()) {
			response.print(validation.errors());
			badRequest();
		}

		RoomOffer offer = seekerFacade.findOffer(id);

		if (offer == null) {
			notFound("not offer with id " + id + " found");
		}

		if (!tokenService.isCorrectToken(authToken, offer)) {
			response.print("WRONG TOKEN");
			badRequest();
		}

		Flatshare flatshare = offer.flatshare;

		OfferFormData formData = new OfferFormData();
		copy(flatshare, offer, formData);

		renderOfferForm(formData);
	}

	public static void editOffer(@Required Long roomOfferId,
			@Valid OfferFormData formData) {
		checkAuthenticity();

		RoomOffer offer = seekerFacade.findOffer(roomOfferId);
		Flatshare flatshare = offer.flatshare;

		copy(formData, flatshare, offer);

		try {
			residentFacade.updateRoomOfferForFlatshare(offer, flatshare);
		} catch (RoomOfferUpdateException e) {
			// TODO (Flo): how to handle this exception
			response.print(e);
			badRequest();
		}
	}

	private static void renderOfferForm(OfferFormData formData) {
		Gender[] genders = Gender.values();
		Floor[] floors = Floor.values();
		SmokingTolerance[] smokingTolerances = SmokingTolerance.values();
		TypeOfHouse[] typesOfHouse = TypeOfHouse.values();
		Appliance[] appliances = Appliance.values();
		AdditionalSpace[] additionalSpaces = AdditionalSpace.values();
		render(formData, genders, floors, smokingTolerances, typesOfHouse,
				appliances, additionalSpaces);
	}

	/**
	 * Copy attributes from form backing object {@code formData} to
	 * {@code flatshare} and {@code offer}.
	 */
	private static void copy(OfferFormData formData, Flatshare flatshare,
			RoomOffer offer) {
		// FLATSHARE
		flatshare.address = new Address(formData.street, formData.streetNumber,
				formData.zipCode, formData.city);
		flatshare.geoLocation = new GeoPt(formData.lat, formData.lng);

		flatshare.streetViewParameters = new StreetViewParameters();
		flatshare.streetViewParameters.displayStreetView = formData.displayStreetView;
		flatshare.streetViewParameters.streetViewGeoLocation = new GeoPt(
				formData.streetViewLat, formData.streetViewLng);
		flatshare.streetViewParameters.streetViewHeading = formData.streetViewHeading;
		flatshare.streetViewParameters.streetViewPitch = formData.streetViewPitch;
		flatshare.streetViewParameters.streetViewZoom = formData.streetViewZoom;

		flatshare.floor = formData.floor;
		// TODO: remove this check (might be related to this issue:
		// https://play.lighthouseapp.com/projects/57987/tickets/1291-play-124-has-npe-when-creatingediting-morphia-entities-with-the-crud-module)
		if (formData.numberOfRooms != null)
			flatshare.numberOfRooms = formData.numberOfRooms;
		flatshare.smokingTolerance = formData.smokingTolerance;
		flatshare.typeOfHouse = formData.typeOfHouse;
		flatshare.appliances = formData.appliances;
		flatshare.additionalSpaces = formData.additionalSpaces;

		// CRITERIA
		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = formData.genders;
		offer.criteria.minAge = formData.minAge;
		offer.criteria.maxAge = formData.maxAge;

		// ROOM DETAILS
		offer.roomDetails = new RoomDetails();
		offer.roomDetails.roomSize = new FloorSpace(formData.roomSize);
		offer.roomDetails.totalRentPerMonthInEuro = formData.totalRentPerMonthInEuro;
		offer.roomDetails.depositInEuro = formData.depositInEuro;
		offer.roomDetails.freeFrom = formData.freeFrom;
		offer.roomDetails.freeTo = formData.freeTo;

		// DESCRIPTION
		offer.description = formData.description;

		// CONTACT DATA
		offer.contactEmail = formData.email;
	}

	/**
	 * Copy attributes from {@code flatshare} and {@code offer} to form backing
	 * object {@code formData};
	 */
	private static void copy(Flatshare flatshare, RoomOffer offer,
			OfferFormData formData) {
		// FLATSHARE

		formData.street = flatshare.address.street;
		formData.streetNumber = flatshare.address.streetNumber;
		formData.zipCode = flatshare.address.zipCode;
		formData.city = flatshare.address.city;

		formData.lat = flatshare.geoLocation.latitude;
		formData.lng = flatshare.geoLocation.longitude;

		formData.displayStreetView = flatshare.streetViewParameters.displayStreetView;
		formData.streetViewLat = flatshare.streetViewParameters.streetViewGeoLocation.latitude;
		formData.streetViewLng = flatshare.streetViewParameters.streetViewGeoLocation.longitude;
		// TODO (Flo): why is it float in the form backing object?
		formData.streetViewHeading = (float) flatshare.streetViewParameters.streetViewHeading;
		formData.streetViewPitch = (float) flatshare.streetViewParameters.streetViewPitch;
		formData.streetViewZoom = (float) flatshare.streetViewParameters.streetViewZoom;

		formData.floor = flatshare.floor;
		formData.numberOfRooms = flatshare.numberOfRooms;
		formData.smokingTolerance = flatshare.smokingTolerance;
		formData.typeOfHouse = flatshare.typeOfHouse;
		formData.appliances = flatshare.appliances;
		formData.additionalSpaces = flatshare.additionalSpaces;

		// CRITERIA
		formData.genders = offer.criteria.genders;
		formData.minAge = offer.criteria.minAge;
		formData.maxAge = offer.criteria.maxAge;

		// ROOM DETAILS
		formData.roomSize = offer.roomDetails.roomSize.squareMeters;
		formData.totalRentPerMonthInEuro = offer.roomDetails.totalRentPerMonthInEuro;
		formData.depositInEuro = offer.roomDetails.depositInEuro;
		formData.freeFrom = offer.roomDetails.freeFrom;
		formData.freeTo = offer.roomDetails.freeTo;

		// DESCRIPTION

		formData.description = offer.description;

		// CONTACT DATA

		formData.email = offer.contactEmail;
	}

	public static void viewAll() {
		ArrayList<RoomOffer> offers = Lists.newArrayList(administrationFacade
				.findAllOffers());
		render(offers);
	}

	public static void viewOffer(long id) {
		RoomOffer offer = seekerFacade.findOffer(id);
		render(offer);
	}

}
