package formation.afpa.fr.Exception;

public class NameNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameNotFoundException() {
		super("Item is not found");
	}
	
	public NameNotFoundException(String message) {
		super(message);
	}

}
