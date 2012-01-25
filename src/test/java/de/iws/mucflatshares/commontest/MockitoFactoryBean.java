package de.iws.mucflatshares.commontest;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

public class MockitoFactoryBean<T> implements FactoryBean<T> {

	private Class<T> type;// the created object type


	public void setType(final Class<T> type) {
		this.type = type;
	}


	@Override
	public T getObject() throws Exception {
		return Mockito.mock(type);
	}


	@Override
	public Class<T> getObjectType() {
		return type;
	}


	@Override
	public boolean isSingleton() {
		return true;
	}
}
