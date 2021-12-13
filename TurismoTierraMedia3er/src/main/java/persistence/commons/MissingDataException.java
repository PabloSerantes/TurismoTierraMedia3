package persistence.commons;

public class MissingDataException extends RuntimeException {

	private static final long serialVersionUID = 6049228196237540507L;

	public MissingDataException(Exception e) {
		super(e);
	}
}
