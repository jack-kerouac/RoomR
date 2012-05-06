package facade.exception;

public class NoUserLoggedInException extends Exception {
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
