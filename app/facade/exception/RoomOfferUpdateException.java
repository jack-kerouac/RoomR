package facade.exception;

/**
 * Thrown when an error occurs during the Roomr Offer Updating Process
 * 
 * @author gernot.pointner@googlemail.com
 * 
 */
public class RoomOfferUpdateException extends Exception {

	public RoomOfferUpdateException(String description) {
		super(description);
	}
}
