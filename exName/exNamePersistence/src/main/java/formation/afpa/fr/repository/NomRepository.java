package formation.afpa.fr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import formation.afpa.fr.entity.Nom;

@Component
public interface NomRepository extends CrudRepository<Nom, Long> {
	
	Nom findByNom (String nom);

}
