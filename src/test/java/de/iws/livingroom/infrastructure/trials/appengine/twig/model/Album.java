package de.iws.livingroom.infrastructure.trials.appengine.twig.model;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.vercer.engine.persist.annotation.Child;
import com.vercer.engine.persist.annotation.Key;

public class Album {

	@Key
	private String photographer;
//	@Key
	private String title;

	@Child
	private Collection<Photo> photos;

	public Album() {
		photos = new LinkedHashSet<Photo>();
	}

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	public Collection<Photo> getPhotos() {
		return photos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((photographer == null) ? 0 : photographer.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (photographer == null) {
			if (other.photographer != null)
				return false;
		} else if (!photographer.equals(other.photographer))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Album [photographer=" + photographer + ", title=" + title + "]";
	}
	
}
