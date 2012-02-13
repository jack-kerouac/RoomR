package models.request;

import models.common.FloorSpace;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class RoomRequest {

	public String city;
	public FloorSpace minRoomSize;
	public double maxRentPerMonthInEuro;

	public Money getMaxRent() {
		return Money.of(CurrencyUnit.EUR, maxRentPerMonthInEuro);
	}
	
}
