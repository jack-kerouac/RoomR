package models.flatshare;

import java.util.Set;

public interface FlatshareRepository {
	
	public void add(Flatshare newFlatshare);
	
	public Set<Flatshare> findAll();
	
	public void update(Flatshare flatshare);
	
	public void remove(Flatshare flatshare);
}
