package models.repos.objectify;

import org.junit.*;

import com.google.appengine.api.datastore.GeoPt;

import java.util.*;
import play.test.*;
import models.*;
import models.common.Address;
import models.common.Floor;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.flatshare.StreetViewParameters;
import models.internal.reposImpl.objectify.ObjectifyFlatshareRepository;


public class ObjectifyFlatshareRepositoryTest extends UnitTest {
	
	private	ObjectifyFlatshareRepository repositoryUnderTest;
	private Flatshare mockedFlatshare;
	
	@Before
	public void setUp(){
		repositoryUnderTest = new ObjectifyFlatshareRepository();
		
		// clear repository
		Set<Flatshare> exisitingFlatshares = repositoryUnderTest.findAll();
		for(Flatshare flatshare : exisitingFlatshares){
			repositoryUnderTest.remove(flatshare);
		}
		
		// initialize mocked Flatshare for testing
		Address mockedAddress = new Address("street", "123", "82061", "city");
		GeoPt mockedGeoPt = new GeoPt(22.98f, 31.412f);
		StreetViewParameters mockedStreetViewParameters = new StreetViewParameters();
		mockedStreetViewParameters.displayStreetView = false;
		mockedStreetViewParameters.streetViewGeoLocation = new GeoPt(1.98f, 8.412f);
		mockedStreetViewParameters.streetViewHeading = 32.3;
		mockedStreetViewParameters.streetViewPitch = 23.2;
		mockedStreetViewParameters.streetViewZoom = 34.2;
		
		mockedFlatshare = new Flatshare();
		mockedFlatshare.address = mockedAddress;
		mockedFlatshare.geoLocation = mockedGeoPt;
		mockedFlatshare.streetViewParameters = mockedStreetViewParameters;
		mockedFlatshare.floor = Floor.forth;
		mockedFlatshare.smokingTolerance = SmokingTolerance.allowedInRoom;
	}
	
	@After
	public void tearDown(){
		repositoryUnderTest = null;
		mockedFlatshare = null;
	}
	
	
	@Test
	/**
	 * persists a flatshare and retrieves it afterwards, checks consistency
	 */
	public void testStorageAndRetrieval(){
		
		// persist mocked FlatshareS
		repositoryUnderTest.add(mockedFlatshare);

		
		// check number of persisted flatshares
		Set<Flatshare> persistedFlatshares = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted",1, persistedFlatshares.size());
		// check consistency of persisted flatshare
		Flatshare persistedFlatshare = (Flatshare) persistedFlatshares.toArray()[0];
		// check address
		assertEquals("street name of persisted address not consistent", 
				mockedFlatshare.address.street,persistedFlatshare.address.street);
		assertEquals("street number of persisted address not consistent", 
				mockedFlatshare.address.streetNumber,persistedFlatshare.address.streetNumber);
		assertEquals("zip Code of persisted address not consistent", 
				mockedFlatshare.address.zipCode,persistedFlatshare.address.zipCode);
		assertEquals("city name of persisted address not consistent", 
				mockedFlatshare.address.city,persistedFlatshare.address.city);
		// check geo pt location
		assertTrue("GeoPt of persisted flatshare is not consistent", 
				persistedFlatshare.geoLocation.equals(mockedFlatshare.geoLocation));
		// check street view parameters
		assertEquals("display street view flag of persisted street view parameters is not consistent",
				mockedFlatshare.streetViewParameters.displayStreetView,
				persistedFlatshare.streetViewParameters.displayStreetView
		);
		assertTrue("street view geo location of persisted street view parameters is not consistent",
				mockedFlatshare.streetViewParameters.streetViewGeoLocation.equals(
						persistedFlatshare.streetViewParameters.streetViewGeoLocation
		));
		assertEquals("street view heading of persisted street view parameters is not consistent",
				mockedFlatshare.streetViewParameters.streetViewHeading,
				persistedFlatshare.streetViewParameters.streetViewHeading, 0.0
		);
		assertEquals("street view pitch of persisted street view parameters is not consistent",
				mockedFlatshare.streetViewParameters.streetViewPitch,
				persistedFlatshare.streetViewParameters.streetViewPitch, 0.0
		);
		assertEquals("street view zoom of persisted street view parameters is not consistent",
				mockedFlatshare.streetViewParameters.streetViewZoom,
				persistedFlatshare.streetViewParameters.streetViewZoom, 0.0
		);
		// check floor
		assertEquals("floor of persisted flatshare is not consistent",
				mockedFlatshare.floor,
				persistedFlatshare.floor
		);
		// check smoking tolerance
		assertEquals("smoking tolerance of persisted flatshare is not consistent",
				mockedFlatshare.smokingTolerance,
				persistedFlatshare.smokingTolerance
		);
	}	
	
	@Test
	public void testUpdate(){
		// persist mocked Flatshare 
		repositoryUnderTest.add(mockedFlatshare);
		
		
		// change geoPt Location and update flatshare entity
		GeoPt newLocation = new GeoPt(23, 8);
		mockedFlatshare.geoLocation = newLocation;
		repositoryUnderTest.update(mockedFlatshare);
		
		// check consistency
		// check number of persisted flatshares
		Set<Flatshare> persistedFlatshares = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted",1, persistedFlatshares.size());
		// check that location has been updated
		Flatshare persistedFlatshare = (Flatshare) persistedFlatshares.toArray()[0];
		assertTrue("Attribute of Flatshare has not been updated",
			mockedFlatshare.geoLocation.equals(persistedFlatshare.geoLocation
		));
	}
	
	@Test
	public void testRemoval(){
		// persist mocked Flatshare 
		repositoryUnderTest.add(mockedFlatshare);
		
		// delete mocked Flatshare
		repositoryUnderTest.remove(mockedFlatshare);
		
		// check that repo is now empty
		// check number of persisted flatshares
		Set<Flatshare> persistedFlatshares = repositoryUnderTest.findAll();
		assertEquals("Flatshare has not been deleted",0, persistedFlatshares.size());
	}
}