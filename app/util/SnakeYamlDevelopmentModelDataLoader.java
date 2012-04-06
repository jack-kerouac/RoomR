package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import play.modules.guice.InjectSupport;
import play.test.Fixtures;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.users.User;

@InjectSupport
public class SnakeYamlDevelopmentModelDataLoader {

	@Inject
	private static RoomOfferRepository offerRepository;

	@Inject
	private static RoomrUserRepository userRepository;

	private static void loadFixtures(File file) {
		Constructor constructor = new Constructor();
		constructor.addTypeDescription(new TypeDescription(RoomrUser.class, "!user"));
		constructor.addTypeDescription(new TypeDescription(Flatshare.class, "!flatshare"));
		constructor.addTypeDescription(new TypeDescription(RoomOffer.class, "!offer"));
		constructor.addTypeDescription(new TypeDescription(RoomOfferApplication.class, "!application"));
		Yaml yaml = new Yaml(constructor);
		
		List<Object> all;
		try {
			all = (List<Object>) yaml.load(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		for(Object o : all)
			System.out.println(o);
		
		
		Map<String, Object> idCache = Fixtures.idCache;

		for (Object o : idCache.values()) {
			if (o instanceof RoomOffer) {
				RoomOffer offer = (RoomOffer) o;
				// TODO: remove
//				offer.flatshare.geoLocation = new GeoPt(48.1505f, 11.5586f);
				// TODO: remove
//				offer.flatshare.roomOffer = offer;
				
				offerRepository.add(offer);
			}
			else if (o instanceof RoomrUser) {
				RoomrUser user = (RoomrUser) o;
				// TODO: remove
				user.gaeUser = new User(user.gaeUserEmail, "gmail.com");

				userRepository.add(user);
			}
			else if (o instanceof Flatshare)
				throw new UnsupportedOperationException(
						"flatshares are persisted through 'cascade' with room offers or users");
		}
	}
	
	public static void main(String[] args) {
		loadFixtures(new File("conf/dev-models/dev-models.yml"));
	}
}
