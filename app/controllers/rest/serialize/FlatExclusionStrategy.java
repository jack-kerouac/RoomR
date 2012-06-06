package controllers.rest.serialize;

import java.util.Collection;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Excludes all field which are {@link Collection}s.
 */
public class FlatExclusionStrategy implements ExclusionStrategy {
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attributes) {
		if (Collection.class.isAssignableFrom(attributes.getDeclaredClass())) {
			return true;
		}

		return false;
	}
}