import java.io.ByteArrayOutputStream;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

import com.google.common.collect.ImmutableMap;

public class CreateOfferTest extends FunctionalTest {

	@Test
	public void testOfferCreation() {
		Response offerFormResponse = GET("/createOffer");
		assertIsOk(offerFormResponse);

		// @formatter:off
		Response createOfferResponse = POST("/offers/",
				ImmutableMap.<String, String> builder().
				put("formData.genders", "male,female").
				put("formData.minAge", "25").put("formData.maxAge", "35").
				put("formData.street", "Knoebelstr.").put("formData.streetNumber", "14").
				put("formData.zipCode", "80538").put("formData.city", "Muenchen").
				put("formData.displayStreetView", "true").
				put("formData.floor", "first").
				put("formData.smokingHabits", "true").
				put("formData.totalRentPerMonthInEuro", "350").
				put("formData.roomSize", "20").
				put("formData.freeFrom", "01.01.2012").put("formData.freeTo", "31.03.2012").
				put("formData.email", "Florian.Rampp@googlemail.com").
			build());
		// @formatter:on

		assertStatus(302, createOfferResponse);
		
		String redirectUrl = createOfferResponse.headers.get("Location").value();
		
		Response offerResponse = GET(redirectUrl);
		assertIsOk(offerResponse);
		
		ByteArrayOutputStream out = offerResponse.out;
	}

}