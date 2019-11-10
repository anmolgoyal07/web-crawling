package me.anmolgoyal.crawling.data;

public class AcknowledgementToken {
	private String token;
	private String message;

	public AcknowledgementToken(String token) {
		super();
		this.token = token;
	}
	
	public AcknowledgementToken(AcknowledgementToken acknowledgementToken) {
		super();
		this.token = acknowledgementToken.token;
	}
	public String getToken() {
		return token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
