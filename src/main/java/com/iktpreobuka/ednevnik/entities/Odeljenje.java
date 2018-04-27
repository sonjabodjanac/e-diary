package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.security.Views;

@Entity
@Table(name="odeljenje")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odeljenje {

	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@JsonView(Views.Public.class)
	private String oznaka;
	
	@JsonView(Views.Admin.class)
  //@JsonManagedReference(value = "razred")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn
	private Razred razred;
	@JsonView(Views.Admin.class)
	@JsonBackReference(value = "odeljenje")
	@OneToMany(mappedBy = "odeljenje",fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
	private List<Ucenik> ucenici = new ArrayList<Ucenik>();
	
	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	
	
	public Odeljenje() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public Razred getRazred() {
		return razred;
	}

	public void setRazred(Razred razred) {
		this.razred = razred;
	}
	
	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}


	
	
	

	
}
