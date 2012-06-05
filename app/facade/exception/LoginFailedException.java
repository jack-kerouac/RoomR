package facade.exception;

public class LoginFailedException extends Exception {
	private final String email;

	public LoginFailedException(String email) {
		super("Failed to log in with email address '" + email + "'");
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
