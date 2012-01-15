package de.iws.livingroom.infrastructure.storage;

import com.google.inject.AbstractModule;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;

public class TwigModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ObjectDatastore.class).to(AnnotationObjectDatastore.class);
	}

}