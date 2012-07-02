package support;

import java.sql.Date;

import models.common.FloorSpace;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.SeekerCriteria;

import com.google.common.collect.Sets;

public final class RoomOfferBuilder {

	private Flatshare flatshare;

	private int minYears = 20;
	private int maxYears = 30;
	private Gender[] genders = { Gender.male, Gender.female };

	private double euro = 300.0;

	private Date freeFrom = new Date(2012, 5, 1);
	private Date freeTo = null;

	private FloorSpace roomSize = new FloorSpace(40.0);

	private RoomOfferBuilder(Flatshare flatshare) {
		this.flatshare = flatshare;
	}

	public static RoomOfferBuilder inFlatshare(Flatshare flatshare) {
		return new RoomOfferBuilder(flatshare);
	}

	public RoomOfferBuilder ageRange(int minYears, int maxYears) {
		this.minYears = minYears;
		this.maxYears = maxYears;
		return this;
	}

	public RoomOfferBuilder genders(Gender... genders) {
		this.genders = genders;
		return this;
	}

	public RoomOfferBuilder rentPerMonth(double euro) {
		this.euro = euro;
		return this;
	}

	public RoomOfferBuilder freeFrom(int year, int month, int day) {
		this.freeFrom = new Date(year, month, day);
		return this;
	}

	public RoomOfferBuilder freeTo(int year, int month, int day) {
		this.freeTo = new Date(year, month, day);
		return this;
	}

	public RoomOfferBuilder roomSize(double squareMeters) {
		this.roomSize = new FloorSpace(squareMeters);
		return this;
	}

	public RoomOffer build() {
		RoomOffer offer = new RoomOffer();

		offer.flatshare = flatshare;

		offer.criteria = new SeekerCriteria();
		offer.criteria.genders = Sets.newHashSet(genders);
		offer.criteria.minAge = minYears;
		offer.criteria.maxAge = maxYears;

		offer.roomDetails = new RoomDetails();
		offer.roomDetails.totalRentPerMonthInEuro = euro;
		offer.roomDetails.freeFrom = freeFrom;
		offer.roomDetails.freeTo = freeTo;
		offer.roomDetails.roomSize = roomSize;

		RoomOffer result = offer.save();
		flatshare.roomOffers.add(result);
		flatshare.save();
		return result;
	}

}
