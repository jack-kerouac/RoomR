package models.request;

import models.common.FloorSpace;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.common.base.Optional;

public class RoomRequest {

	public String city;
	public FloorSpace minRoomSize;
	public Double maxRentPerMonthInEuro;

	public Optional<FloorSpace> getMinRoomSize() {
		if (minRoomSize == null || minRoomSize.squareMeters == null)
			return Optional.<FloorSpace> absent();
		else
			return Optional.<FloorSpace> of(minRoomSize);
	}

	public Optional<Money> getMaxRent() {
		if (maxRentPerMonthInEuro == null)
			return Optional.<Money> absent();
		else
			return Optional.<Money> of(Money.of(CurrencyUnit.EUR, maxRentPerMonthInEuro));
	}

}
