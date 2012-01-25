package de.iws.mucflatshares.commontest;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSubsystemAssemblyTest {

	private final static Logger logger = LoggerFactory
			.getLogger(AbstractSubsystemAssemblyTest.class);

	@Autowired
	private ApplicationContext context;

	private String subsystemBasePackage;

	public AbstractSubsystemAssemblyTest() {
		this.subsystemBasePackage = getClass().getPackage().getName()
				.toString();
	}

	public AbstractSubsystemAssemblyTest(String subsystemBasePackage) {
		this.subsystemBasePackage = subsystemBasePackage;
	}

	@Test
	public void testIntegration() {
		logger.info("loaded Spring beans: "
				+ Arrays.toString(context.getBeanDefinitionNames()));

		StringBuilder loadedDomainBeans = new StringBuilder();

		String[] names = context.getBeanDefinitionNames();
		for (String name : names) {
			Object bean = context.getBean(name);
			if (bean.getClass().getPackage() != null
					&& bean.getClass().getPackage().getName()
							.startsWith(subsystemBasePackage))
				loadedDomainBeans.append(name + ", ");
		}

		int l = loadedDomainBeans.length();
		if (l > 0)
			loadedDomainBeans.delete(l - 2, l);
		logger.info("loaded domain beans: [" + loadedDomainBeans.toString()
				+ "]");
	}

}
