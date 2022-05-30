package util;

public class MailTransmissionFailureException extends Exception{

	private static final long serialVersionUID = 1L;

	public MailTransmissionFailureException(String msg) {
		super(msg);
	}

}