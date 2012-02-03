package models.internal.reposImpl.twig;

import models.seeking.FlatshareSeeker;
import models.seeking.FlatshareSeekerRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.Inject;

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
