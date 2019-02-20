package formation.afpa.fr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "nom")
public class Nom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column(name="nom", nullable = false, length = 30)
	private String nom;
	
	
	
	
	public Nom() {
		
	}




	public Nom(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNom() {
		return nom;
	}




	public void setNom(String nom) {
		this.nom = nom;
	}




	@Override
	public String toString() {
		return "Nom [id=" + id + ", nom=" + nom + "]";
	}
	

	
}
