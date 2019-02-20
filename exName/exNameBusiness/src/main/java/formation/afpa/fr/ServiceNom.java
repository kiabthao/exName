package formation.afpa.fr;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.sym.Name;

import formation.afpa.fr.Exception.NameAlreadyExistsException;
import formation.afpa.fr.Exception.NameNotAvailableException;
import formation.afpa.fr.Exception.NameNotFoundException;
import formation.afpa.fr.Exception.NameNotValidException;
import formation.afpa.fr.InterfaceService.IService;
import formation.afpa.fr.entity.Nom;
import formation.afpa.fr.repository.NomRepository;

@Service
public class ServiceNom implements IService<Nom, Exception> {

	@Autowired
	NomRepository repo;

	@Override
	public List<Nom> findAll() throws Exception {
		List<Nom> findAll = (List<Nom>) repo.findAll();
		
		
		
			return findAll;
		
	}

	@Override
	public Nom findById(Long id) throws Exception {
		if (id == null) {
			throw new Exception();
		}
		Nom nomA = repo.findById(id).get();

		if (nomA == null) {
			throw new Exception();
		}
		return nomA;
	}

	@Override
	public Nom create(Nom nomA) throws Exception {

		if (nomA == null) {
			throw new Exception();

		}
		if (nomA.getId() != null) {
			Optional<Nom> nomOptionnal = repo.findById(nomA.getId());

			if (nomOptionnal.isPresent()) {
				throw new NameNotAvailableException();
			} else {
				throw new NameAlreadyExistsException();

			}
		}
		return repo.save(nomA);
	}

	@Override
	public Nom update(Nom nomA) throws Exception {
		if ((nomA == null) || (nomA.getId() == null)) {
			throw new Exception();
		}
		Long nomAChercher = nomA.getId();
		Optional<Nom> nomOptional = repo.findById(nomAChercher);
		
		if (!nomOptional.isPresent()) {
			throw new Exception();
		}
	return repo.save(nomA);

	}

	@Override
	public void deleteById(Long id) throws Exception {
		if (id == null) {
			throw new Exception();
		}
		Optional<Nom> nomOptional = repo.findById(id);
		if (!nomOptional.isPresent()) {
			throw new Exception("name with requested id does not exist");
		}
		repo.deleteById(id);
		
	}

	@Override
	public void delete(Nom nomA) throws Exception {
		if (nomA == null) {
			throw new NameNotValidException();
		} else if (nomA.getId() == null) {
			throw new NameNotFoundException();
		}
		if (!findAll().contains(nomA)) {
			throw new NameNotAvailableException();
		}
		repo.delete(nomA);

	}
	public Nom findByNom(String nom) {
		Nom nom1 = repo.findByNom(nom);
		return nom1;
	}

}
