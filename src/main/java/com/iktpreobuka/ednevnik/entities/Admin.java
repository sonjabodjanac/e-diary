package com.iktpreobuka.ednevnik.entities;

import java.util.Date;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.ednevnik.enums.TipOsobe;

@Entity(name = "admin")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Admin extends Osoba{

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Integer id, String ime, String prezime, String jmbg, Date datumRodjenja, TipOsobe tipOsobe, Integer verzija) {
		super(id, ime, prezime, jmbg, datumRodjenja, tipOsobe, verzija);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
