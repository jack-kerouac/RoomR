package test.functional;

import org.apache.http.HttpStatus;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

import com.google.common.collect.ImmutableMap;

import controllers.Registration;

public class RegistrationTest extends FunctionalTest {
	private static final String LOCATION_HEADER = "Location";

	@Test
	public void testRegistrationFormRedirectsIfNoGAEUser() {
		Response response = GET(getRegistrationFormUrl("/"));
		assertStatus(HttpStatus.SC_MOVED_TEMPORARILY, response);
	}

	@Test
	public void testRegistrationFormIfGAEUser() {
		loginToGAE("not_existing@not_existing.de");

		Response response = GET(getRegistrationFormUrl("/"));
		assertIsOk(response);
	}

	@Test
	public void testRegistrationFormInvalidData() {
		loginToGAE("not_existing@not_existing.de");

		Response response = POST(getRegistrationPostUrl("/"), //
				ImmutableMap.of("formData.name", "", //
						"formData.birthdate", "01.01.1970", //
						"formData.gender", "male"));

		assertStatus(HttpStatus.SC_MOVED_TEMPORARILY, response);
		String location = response.headers.get(LOCATION_HEADER).value();
		assertTrue(location.startsWith("/register"));
		assertTrue(location.contains("redirectUrl=%2F"));
		assertTrue(location.contains("formData.name="));
		assertTrue(location.contains("formData.birthdate=01.01.1970"));
		assertTrue(location.contains("formData.gender=male"));
	}

	@Test
	public void testRegistrationFormSuccess() {
		loginToGAE("test@test.de");

		Response response = POST(getRegistrationPostUrl("/"), //
				ImmutableMap.of("formData.name", "Test", //
						"formData.birthdate", "01.01.1970", //
						"formData.gender", "male"));

		assertStatus(HttpStatus.SC_MOVED_TEMPORARILY, response);
		String location = response.headers.get(LOCATION_HEADER).value();
		assertTrue(location.equals("/"));
	}

	private URL getRegistrationFormUrl(String redirectUrl) {
		URL result = reverse();
		{
			Registration.registrationForm(redirectUrl, null);
		}
		return result;
	}

	private URL getRegistrationPostUrl(String redirectUrl) {
		URL result = reverse();
		{
			Registration.register(redirectUrl, null);
		}
		return result;
	}

	private void loginToGAE(String email) {
		Response response = POST("/_ah/login", //
				ImmutableMap.of("email", email, //
						"url", "/"));
		assertStatus(HttpStatus.SC_MOVED_TEMPORARILY, response);
	}
}
