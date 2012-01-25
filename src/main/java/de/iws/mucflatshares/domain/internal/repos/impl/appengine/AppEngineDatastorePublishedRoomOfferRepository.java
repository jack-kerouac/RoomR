package de.iws.mucflatshares.domain.internal.repos.impl.appengine;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.market.PublishedRoomOffer;
import de.iws.mucflatshares.domain.market.PublishedRoomOfferRepository;
import de.iws.mucflatshares.domain.offering.offer.Pricing;
import de.iws.mucflatshares.domain.offering.offer.Room;
import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

//@Repository
public class AppEngineDatastorePublishedRoomOfferRepository implements PublishedRoomOfferRepository {

	@Override
	public Collection<PublishedRoomOffer> findAllThatMatch(RoomFilter request, PersonalProfile profile) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<PublishedRoomOffer> findAllThatMatch(RoomFilter request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(PublishedRoomOffer offer) {
		// TODO Auto-generated method stub

		Key publishedOfferKey = KeyFactory.createKey("Offers", offer.getOffer().getId());
		Entity offerEntity = new Entity("PublishedRoomOffer", offer.getOffer().getId());
		offerEntity.setProperty("publicationDate", offer.getPublishingDate().toDate());
		offerEntity.setProperty("room", offer.getOffer().getRoom());
		offerEntity.setProperty("pricing", offer.getOffer().getPricing());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(offerEntity);
	}

	@Override
	public PublishedRoomOffer findById(long offerId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key publishedOfferKey = KeyFactory.createKey("Offers", offerId);
		// Run an ancestor query to ensure we see the most up-to-date
		// view of the Greetings belonging to the selected Guestbook.
		Query query = new Query("PublishedRoomOffer", publishedOfferKey);
		Entity offerEntity = datastore.prepare(query).asSingleEntity();

		RoomOffer offer = new RoomOffer(offerId, null, null, (Pricing) offerEntity.getProperty("pricing"),
				(Room) offerEntity.getProperty("room"), null);

		return new PublishedRoomOffer(offer, (DateTime)offerEntity.getProperty("publicationDate"));
	}
}
