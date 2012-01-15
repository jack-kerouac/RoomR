package de.iws.livingroom.infrastructure.trials.appengine.twig;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableList;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;

import de.iws.livingroom.infrastructure.trials.appengine.twig.model.Album;
import de.iws.livingroom.infrastructure.trials.appengine.twig.model.Coordinates;
import de.iws.livingroom.infrastructure.trials.appengine.twig.model.LandscapePhoto;
import de.iws.livingroom.infrastructure.trials.appengine.twig.model.PhotoWithPersons;
import de.iws.livingroom.infrastructure.trials.appengine.twig.model.SocialEvent;

public class TwigModelTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	private SocialEvent createInstance() {
		Album album = new Album();
		album.setPhotographer("florian");
		album.setTitle("title");

		album.addPhoto(new PhotoWithPersons().setNames(ImmutableList.<String> of("flo", "bärbel")).setDateTaken(
				new Date(1212121)));
		album.addPhoto(new PhotoWithPersons().setNames(ImmutableList.<String> of("christoph")).setDateTaken(
				new Date(1212122)));
		album.addPhoto(new LandscapePhoto().setLocation(new Coordinates(1.0, 2.0)).setDateTaken(new Date(1212123)));

		SocialEvent event = new SocialEvent();
		event.setTitle("Woodstock");
		event.setAlbum(album);
		return event;
	}

	@Test
	public void testPersist() {
		SocialEvent event = createInstance();

		// create a new light-weight stateful datastore for every request
		ObjectDatastore datastore = new AnnotationObjectDatastore();

		// store the instance and all other reachable instances
		Key key = datastore.store(event);

		datastore.disassociateAll();

		SocialEvent result = datastore.load(SocialEvent.class, "Woodstock");
		Assert.assertEquals(event, result);
		Assert.assertEquals(event.getAlbum(), result.getAlbum());
		Assert.assertTrue(result.getAlbum().getPhotos().containsAll(event.getAlbum().getPhotos()));

		System.out.println(key);
	}
}
