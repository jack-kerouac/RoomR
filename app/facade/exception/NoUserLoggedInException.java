package facade.exception;

public class NoUserLoggedInException extends RuntimeException {
	public NoUserLoggedInException() {
		super();
	}

	public NoUserLoggedInException(String message, Throwable t) {
		super(message, t);
	}

	public NoUserLoggedInException(String message) {
		super(message);
	}

	public NoUserLoggedInException(Throwable t) {
		super(t);
	}
}
