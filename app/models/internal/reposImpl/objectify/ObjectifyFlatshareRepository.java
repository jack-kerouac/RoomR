package models.internal.reposImpl.objectify;

import java.util.Set;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import play.modules.objectify.Datastore;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Query;

public class ObjectifyFlatshareRepository implements FlatshareRepository{

	@Override
	public void add(Flatshare newFlatshare) {
		Datastore.put(newFlatshare);
	}

	@Override
	public Set<Flatshare> findAll() {
		Query<Flatshare> query = Datastore.query(Flatshare.class);
		return ImmutableSet.copyOf(query);
	}

	@Override
	public void update(Flatshare flatshare) {
		Datastore.put(flatshare);		
	}

	@Override
	public void remove(Flatshare flatshare) {
		Datastore.delete(flatshare);
	}
}