package facade.exception;

public class NoAuthenticationProviderUserLoggedInException extends NoUserLoggedInException {
	public NoAuthenticationProviderUserLoggedInException() {
		super();
	}

	public NoAuthenticationProviderUserLoggedInException(String message, Throwable t) {
		super(message, t);
	}

	public NoAuthenticationProviderUserLoggedInException(String message) {
		super(message);
	}

	public NoAuthenticationProviderUserLoggedInException(Throwable t) {
		super(t);
	}
}
