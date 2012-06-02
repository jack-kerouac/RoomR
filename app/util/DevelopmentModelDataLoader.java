package util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import play.modules.guice.InjectSupport;

/**
 * Loads dev models from specified YAML file using SnakeYAML and uses
 * {@link RoomOfferRepository}, {@link RoomrUserRepository}, and
 * {@link RoomOfferApplicationRepository} to store loaded objects into
 * datastore.
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 */
@InjectSupport
public class DevelopmentModelDataLoader {

	@Inject
	private static RoomOfferRepository offerRepository;

	@Inject
	private static RoomrUserRepository userRepository;

	@Inject
	private static RoomOfferApplicationRepository applicationRepository;

	@Inject
	private static FlatshareRepository flatshareRepository;

	public static void loadFixtures(InputStream is) {
		Constructor constructor = new Constructor();
		constructor.addTypeDescription(new TypeDescription(RoomrUser.class, "!user"));
		constructor.addTypeDescription(new TypeDescription(Flatshare.class, "!flatshare"));
		constructor.addTypeDescription(new TypeDescription(RoomOffer.class, "!offer"));
		constructor.addTypeDescription(new TypeDescription(RoomOfferApplication.class, "!application"));
		Yaml yaml = new Yaml(constructor);

		List<Object> all;
		all = (List<Object>) yaml.load(new InputStreamReader(is));

		for (Object o : all) {
			if (o instanceof RoomrUser)
				userRepository.add((RoomrUser) o);
			else if (o instanceof RoomOffer)
				offerRepository.add((RoomOffer) o);
			else if (o instanceof RoomOfferApplication)
				applicationRepository.add((RoomOfferApplication) o);
			else if (o instanceof Flatshare)
				flatshareRepository.add((Flatshare) o);
		}
	}
}
