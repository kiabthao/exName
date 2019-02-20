package formation.afpa.fr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import formation.afpa.fr.Exception.NameAlreadyExistsException;
import formation.afpa.fr.Exception.NameNotAvailableException;
import formation.afpa.fr.Exception.NameNotFoundException;
import formation.afpa.fr.Exception.NameNotValidException;
import formation.afpa.fr.entity.Nom;
import formation.afpa.fr.repository.NomRepository;


@RunWith(MockitoJUnitRunner.class) // On indique qu’on utilise Mockito
public class TestServiceNom {

	@Mock // Ici on indique qu’on a un mock qui simule le repository de Specie
	private NomRepository repoMock;

	@InjectMocks // Ici, on indique que le mock est injecté/utilisé par le service à tester (ici
					// ServiceSpecie)
	private ServiceNom service;

	// on va utiliser le mock comme si c’était un repository.

	List<Nom> list = new ArrayList<>();
	String nom = "bibi";
	Long id = 1l;
	int listSize;

	@Before
	public void setUp() {
		list.add(new Nom(0l, "bibi"));
		list.add(new Nom(1l, "baba"));
		listSize = list.size();

		// used in several tests
		
		when(repoMock.findById(id)).thenReturn(Optional.of(getNomById(id)));
		when(repoMock.findById(0l)).thenReturn(Optional.of(getNomById(0l)));
		when(repoMock.findById(1l)).thenReturn(Optional.of(getNomById(1l)));
		
		when(repoMock.findAll()).thenReturn(list);

	}

	@Test
	public void findAll() throws Exception {

		// test the list's size

		try {
			assertEquals(listSize, service.findAll().size());
		} catch (NameNotAvailableException e) {
			// assertTrue(false);
			Assert.fail("Test failed : NameNotAviableException was not expected");
		}

		// initialise the list to 0 and test that the exception is well thrown
		list = new ArrayList<Nom>();
		try {
			service.findAll();
		} catch (NameNotAvailableException e) {
			e.printStackTrace();
			assertTrue(true);
		}

	}

	
	@Test
	public void findById() throws Exception {
		when(repoMock.findById(id)).thenReturn(Optional.of(getNomById(id)));
		try {
			assertNotNull(service.findById(id));
			assertEquals(service.findById(id).getNom(), "baba");

		} catch (NameNotFoundException e) {
			Assert.fail("Test failed : NameNotFoundException was not expected");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(true);
		}

		try {
			service.findById(null);
		} catch (Exception e) {
			assertTrue(true);
		}

	}

	private Nom getNomById(Long id) {
		Nom n = null;

		for (Nom noml : list) {
			if (noml.getId().equals(id)) {
				n = noml;
			}

		}
		return n;
	}

	
	@Test
	public void create() throws Exception {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] os = invocation.getArguments();
				if (os != null && os.length != 0 && os[0] != null) {
					Nom it = (Nom) os[0];
					it.setId(12L);
					list.add(it);
				}
				return null;
			}
		}).when(repoMock).save(Mockito.any(Nom.class));
		// refactored=>
		// when(repoMock.findById(id)).thenReturn(Optional.of(getItemById(id)));

		Nom it = new Nom();
		it.setNom("kk");
		
		try {
			service.create(it);
		} catch (NameNotValidException e) {
			Assert.fail("Test failed : NameNotValidException was not expected");
		} catch (NameAlreadyExistsException e) {
			Assert.fail("Test failed : NameAlreadyExistsException was not expected");
		}

		assertEquals(list.size(), listSize + 1);
		assertEquals(12L, list.get(2).getId().longValue());

		try {
			service.create(null);
		} catch (Exception e) {
			assertTrue(true);

		}
	}

	@Test
	public void update() throws Exception {

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] os = invocation.getArguments();
				if (os != null && os.length != 0 && os[0] != null) {
					Nom it = (Nom) os[0];
					Integer indexOf = list.indexOf(it);

					if (indexOf != null) {
						Nom oldNom = list.get(indexOf);
						oldNom.setId(it.getId());
						oldNom.setNom(it.getNom());
						
					}
				}
				return null;
			}
		}).when(repoMock).save(Mockito.any(Nom.class));
	//	when(repoMock.findById(id)).thenReturn(Optional.of(getItemById(id)));

		Nom it = service.findById(0l);
		it.setNom("AA");

		try {
			service.update(it);
		} catch (NameNotValidException e) {
			Assert.fail("Test failed: NameNotValidException was not expected");
		} catch (NameNotFoundException e) {
			Assert.fail("Test failed: NameNotFoundException was not expected");
		}
		assertEquals(listSize, list.size());
		assertEquals(0L, list.get(0).getId().longValue());
		assertEquals("AA", service.findById(0l).getNom());

		try {
			service.update(null);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void delete() throws Exception {

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] os = invocation.getArguments();
				if (os != null && os.length != 0 && os[0] != null) {
					Nom it = (Nom) os[0];

					if (list.contains(it)) {
						list.remove(it);
					}
				}
				return null;
			}
		}).when(repoMock).delete(Mockito.any(Nom.class));
		
		try {
			Nom it = service.findById(0l);
			it.setId(null);
			service.delete(it);

		} catch (NameNotFoundException e) {
			assertTrue(true);

		} catch (NameNotValidException e) {
			Assert.fail("Test failed : NameNotValidException was not expected");

		} catch (NameNotAvailableException e) {
			Assert.fail("Test failed : NameNotAvailableException was not expected");

		}

		// trying to delete a null specie
		try {
			service.delete(null);

		} catch (NameNotFoundException e) {
			Assert.fail("Test failed : ItemNotFoundException was not expected");

		} catch (NameNotValidException e) {
			assertTrue(true);

		} catch (NameNotAvailableException e) {
			Assert.fail("Test failed : ItemNotAvailableException was not expected");

		}

		// case where you try to delete an object that is not the the DB
		Nom it3 = new Nom();
		
		it3.setNom("AA");
		
		
		
		try {
			service.delete(it3);

		} catch (NameNotFoundException e) {
			assertTrue(true);

		} catch (NameNotValidException e) {
			Assert.fail("Test failed : NameNotValidException was not expected");

		} catch (NameNotAvailableException e) {
			assertTrue(true);
		}

		// normal case
		assertNotNull(service.findById(1l));
		Nom it = service.findById(1l);
		try {
			service.delete(it);
			assertEquals(listSize - 1, list.size());
		} catch (NameNotFoundException e) {
			Assert.fail("Test failed : NameNotFoundException was not expected");
		} catch (NameNotValidException e) {
			Assert.fail("Test failed : NameNotValidException was not expected");
		} catch (NameNotAvailableException e) {
			Assert.fail("Test failed : NameNotAvailableException was not expected");
		}

	}

	@Test
	public void deleteById() throws Exception {

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] os = invocation.getArguments();
				if (os != null && os.length != 0 && os[0] != null) {
					Long id = (Long) os[0];

					for (Nom i : list) {
						if(i.getId() == id) {
							list.remove(i);
							break;
						}
					}
					
				}
				return null;
			}
		}).when(repoMock).deleteById(Mockito.any(Long.class));

		try {
			service.deleteById(0l);
		} catch (NameNotFoundException e) {
			Assert.fail();
		}
		assertEquals(listSize - 1, list.size());

		try {
			service.deleteById(null);
		
		} catch (Exception e) {
			assertTrue(true);
		}
	}

}
