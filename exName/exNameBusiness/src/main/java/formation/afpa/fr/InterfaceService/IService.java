package formation.afpa.fr.InterfaceService;

import java.util.List;



public interface IService<T, E extends Exception> {

	public List<T> findAll() throws E;

	public T findById(Long id) throws E;

	public T create(T t) throws E;

	public T update(T t) throws E;

	public void deleteById(Long id) throws E;

	public void delete(T t) throws E;

}
