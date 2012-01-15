package de.iws.livingroom.domain.internal.reposImpl.twig;

import com.google.inject.Inject;
import com.vercer.engine.persist.ObjectDatastore;

import de.iws.livingroom.domain.seeking.FlatshareSeeker;
import de.iws.livingroom.domain.seeking.FlatshareSeekerRepository;

public class TwigFlatshareSeekerRepository implements FlatshareSeekerRepository {
	private final ObjectDatastore datastore;

	@Inject
	public TwigFlatshareSeekerRepository(ObjectDatastore datastore) {
		super();
		this.datastore = datastore;
	}

	@Override
	public FlatshareSeeker findForId(String emailAddress) {
		return datastore.load(FlatshareSeeker.class, emailAddress);
	}

	@Override
	public void add(FlatshareSeeker seeker) {
		datastore.store(seeker);
	}

}
