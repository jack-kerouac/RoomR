package controllers.rest.serialize;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class NameBasedExclusionStrategy implements ExclusionStrategy {
	private final Map<Class<?>, Set<String>> excludedFieldsPerClass = Maps
			.newHashMap();

	public NameBasedExclusionStrategy withExclusionsFor(Class<?> clazz,
			Set<String> excludedFields) {
		excludedFieldsPerClass.put(clazz, ImmutableSet.copyOf(excludedFields));
		return this;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes attributes) {
		Set<String> excludedFields = excludedFieldsPerClass.get(attributes
				.getDeclaringClass());

		if (excludedFields == null) {
			return false;
		}

		return excludedFields.contains(attributes.getName());
	}
}