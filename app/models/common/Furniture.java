package models.common;

import java.util.ArrayList;
import java.util.Collection;

public final class Furniture {

	private final Collection<FurnitureItem> items;

	public Furniture() {
		super();
		this.items = new ArrayList<FurnitureItem>();
	}

	public boolean containsAll(Furniture required) {
		return items.containsAll(required.items);
	}

	public void addItem(FurnitureItem item) {
		items.add(item);
	}

}
