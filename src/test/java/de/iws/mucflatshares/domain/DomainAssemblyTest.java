package de.iws.mucflatshares.domain;

import org.springframework.test.context.ContextConfiguration;

import de.iws.mucflatshares.commontest.AbstractSubsystemAssemblyTest;

@ContextConfiguration(locations = { "/domain.xml", "/test.xml" })
public class DomainAssemblyTest extends AbstractSubsystemAssemblyTest {

}
