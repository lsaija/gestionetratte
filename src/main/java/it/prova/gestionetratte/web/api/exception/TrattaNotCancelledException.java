package it.prova.gestionetratte.web.api.exception;

public class TrattaNotCancelledException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TrattaNotCancelledException(String message) {
		super(message);
	}

}
