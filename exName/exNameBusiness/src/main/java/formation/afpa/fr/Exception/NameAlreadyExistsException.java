package formation.afpa.fr.Exception;

public class NameAlreadyExistsException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameAlreadyExistsException() {
		super("Item already exists");
	}
}
