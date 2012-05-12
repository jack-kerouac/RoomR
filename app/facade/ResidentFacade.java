package facade;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import notifiers.RoomrMailer;

public class ResidentFacade {
	public void createFlatshareAndOffer(Flatshare newFlatshare,
			RoomOffer roomOffer) {
		Flatshare.create(newFlatshare);
		roomOffer.flatshare = newFlatshare;
		RoomOffer.create(roomOffer);

		RoomrMailer.offerCreated(roomOffer);
	}

}
