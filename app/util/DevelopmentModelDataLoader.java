package util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import models.application.RoomOfferApplication;
import models.common.GeoPt;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import play.db.jpa.GenericModel;
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

	public static void loadFixtures(InputStream is) {
		Constructor constructor = new Constructor();
		constructor.addTypeDescription(new TypeDescription(RoomrUser.class,
				"!user"));
		constructor.addTypeDescription(new TypeDescription(Flatshare.class,
				"!flatshare"));
		constructor.addTypeDescription(new TypeDescription(RoomOffer.class,
				"!offer"));
		constructor.addTypeDescription(new TypeDescription(
				RoomOfferApplication.class, "!application"));
		constructor.addTypeDescription(new TypeDescription(GeoPt.class,
				"!geoPt"));
		Yaml yaml = new Yaml(constructor);

		List<Object> all;
		try {
			all = (List<Object>) yaml.load(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		for (Object o : all) {
			if (o instanceof GenericModel)
				((GenericModel) o).merge();
		}
	}
}
