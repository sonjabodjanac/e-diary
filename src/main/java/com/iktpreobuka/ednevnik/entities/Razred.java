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

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iktpreobuka.ednevnik.security.Views;

@Entity
@Table(name="razred")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Razred {
	
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@JsonView(Views.Public.class)
	private Integer brRazreda;
	@JsonView(Views.Public.class)
	private String skGodina;
	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	@JsonView(Views.Admin.class)
	@JsonBackReference(value= "razred")
	@OneToMany(mappedBy = "razred",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Odeljenje> odeljenja = new ArrayList<>();
	

	public Razred() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBrRazreda() {
		return brRazreda;
	}

	public void setBrRazreda(Integer brRazreda) {
		this.brRazreda = brRazreda;
	}

	public List<Odeljenje> getOdeljenja() {
		return odeljenja;
	}

	public void setOdeljenja(List<Odeljenje> odeljenja) {
		this.odeljenja = odeljenja;
	}

	public String getSkGodina() {
		return skGodina;
	}

	public void setSkGodina(String skGodina) {
		this.skGodina = skGodina;
	}

	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}


	
	
	
	
}
