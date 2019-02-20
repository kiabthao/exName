package formation.afpa.fr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import formation.afpa.fr.entity.Nom;
import formation.afpa.fr.repository.NomRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = SmallApp.class)
public class NomTest extends TestFather {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NomRepository repo;

	@Test
	public void List() {
		try {
			List<Nom> list = (List<Nom>) repo.findAll();
			assertNotNull(list);
			assertEquals(list.size(), 6);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	public void findById() {
		try {
			String nom = "bobo";
			Nom nom1 = repo.findById(idLastNom).get();

			assertNotNull(nom1);
			assertEquals(nom1.getNom(), nom);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void add() {
		try {
			Nom nom1 = new Nom(null, "tk");
			repo.save(nom1);

			assertNotNull(nom1);
			assertEquals(repo.count(), 6);

		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void update() {
		try {
			assertNotNull(idLastNom);

			String newNom = "nn";
			Nom nom1 = repo.findById(idLastNom).get();

			assertNotEquals(nom1.getNom(), newNom);

			nom1.setNom(newNom);
			repo.save(nom1);

			Nom nomFromDB = repo.findById(idLastNom).get();
			assertEquals(nomFromDB.getNom(), newNom);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@Test
	public void delete() {
		Nom nom1 = new Nom(null, "tt");
		repo.save(nom1);
		assertEquals(repo.count(), 6);
		
		repo.delete(nom1);
		assertEquals(repo.count(), 5);
	}

}
