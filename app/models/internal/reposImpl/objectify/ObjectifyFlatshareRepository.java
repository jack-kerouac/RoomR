package models.internal.reposImpl.objectify;

import java.util.Set;

import play.modules.objectify.Datastore;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;

public class ObjectifyFlatshareRepository implements FlatshareRepository{

	@Override
	public void add(Flatshare newFlatshare) {
		Datastore.put(newFlatshare);
	}

	@Override
	public Set<Flatshare> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Flatshare flatshare) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Flatshare flatshare) {
		// TODO Auto-generated method stub
		
	}

}