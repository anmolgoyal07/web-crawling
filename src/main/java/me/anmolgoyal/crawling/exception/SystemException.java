package me.anmolgoyal.crawling.exception;



public class SystemException extends RuntimeException {
	
	private static final long serialVersionUID = -1961522112511805799L;

	private final String userMessage;
	
	public SystemException(String message, Throwable cause, String userMessage) {
		super(message, cause);
		this.userMessage = userMessage;
	}

	public SystemException(String message, String userMessage) {
		super(message);
		this.userMessage = userMessage;
	}

	public SystemException(Throwable cause, String userMessage) {
		super(cause);
		this.userMessage = userMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}
	

}
