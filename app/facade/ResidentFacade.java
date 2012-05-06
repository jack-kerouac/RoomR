package facade;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import notifiers.RoomrMailer;

public class ResidentFacade {

	@Inject
	private FlatshareRepository flatshareRepository;

	@Inject
	private RoomOfferRepository roomOfferRepository;

	public void createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer) {
		flatshareRepository.add(newFlatshare);
		roomOffer.setFlatshare(newFlatshare);
		roomOfferRepository.add(roomOffer);
		RoomrMailer.offerCreated(roomOffer);
	}

}
