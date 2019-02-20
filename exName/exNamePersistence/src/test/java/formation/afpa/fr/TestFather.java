package formation.afpa.fr;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;
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
public class TestFather {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NomRepository nomRepo;

	public Long idLastNom = 0l;

	@Before
	public void setUp() {
		initBdd();

	}

	@Test
	public void test() {
		assertTrue(true);
	}

	public void initBdd() {
	

	Nom nom1 = new Nom(null, "bibi");
	Nom nom2 = new Nom(null, "baba");
	Nom nom3 = new Nom(null, "bubu");
	Nom nom4 = new Nom(null, "bebe");
	Nom nom5 = new Nom(null, "bobo");
	
	entityManager.persist(nom1);
	entityManager.persist(nom2);
	entityManager.persist(nom3);
	entityManager.persist(nom4);
	entityManager.persist(nom5);
	
	idLastNom = (Long) entityManager.persistAndGetId(nom5);
	
	
	}
}
