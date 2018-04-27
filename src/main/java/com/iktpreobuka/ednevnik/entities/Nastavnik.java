package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.enums.TipOsobe;
import com.iktpreobuka.ednevnik.security.Views;

@Entity(name = "nastavnik")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Nastavnik extends Osoba {
	
	
	@JsonView(Views.Private.class)
	@JsonBackReference(value = "ocena_nastavnik")
	@OneToMany(mappedBy="nastavnik",fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Ocena> ocene = new ArrayList<>();
	

	public Nastavnik() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Nastavnik(Integer id, String ime, String prezime, String jmbg,Date datumRodjenja, TipOsobe tipOsobe, Integer verzija) {
		super(id, ime, prezime, jmbg,datumRodjenja,tipOsobe, verzija);
	}


	public List<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(List<Ocena> ocene) {
		this.ocene = ocene;
	}


	
	

}
