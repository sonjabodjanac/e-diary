package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iktpreobuka.ednevnik.security.Views;

@Entity
@Table(name="predmet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Predmet {
	
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@JsonView(Views.Public.class)
	private String naziv;
	@JsonView(Views.Admin.class)
	private Integer fond;
	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	@JsonView(Views.Admin.class)
	@JsonBackReference(value = "ocena_predmet")
	@OneToMany(mappedBy = "predmet",cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
	private List<Ocena> ocena = new ArrayList<>();

	public Predmet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getFond() {
		return fond;
	}

	public void setFond(Integer fond) {
		this.fond = fond;
	}

	public List<Ocena> getOcena() {
		return ocena;
	}

	public void setOcena(List<Ocena> ocena) {
		this.ocena = ocena;
	}
	

	

}
