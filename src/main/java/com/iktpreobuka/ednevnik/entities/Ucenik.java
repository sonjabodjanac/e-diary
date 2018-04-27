package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import com.iktpreobuka.ednevnik.enums.TipOsobe;
import com.iktpreobuka.ednevnik.security.Views;

@Entity(name = "ucenik")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ucenik extends Osoba {
     
    @JsonView(Views.Admin.class)
	//@JsonManagedReference(value = "roditelj")
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn
	private Roditelj roditelj;
	
    @JsonView(Views.Admin.class)
	//@JsonManagedReference(value = "odeljenje")
	@ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn
	private Odeljenje odeljenje;
    
    @JsonView(Views.Admin.class)
  	@JsonBackReference(value = "ocena_ucenik")
  	@OneToMany(mappedBy = "ucenik",cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
  	private List<Ocena> ocena = new ArrayList<>();
	

	public Ucenik() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Ucenik(Integer id, String ime, String prezime, String jmbg,Date datumRodjenja, TipOsobe tipOsobe, Integer verzija,Roditelj roditelj, Odeljenje odeljenje) {
		super(id, ime, prezime, jmbg,datumRodjenja,tipOsobe, verzija);
		this.roditelj = roditelj;
		this.odeljenje = odeljenje;
	
	}

	public Roditelj getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(Roditelj roditelj) {
		this.roditelj = roditelj;
	}

	public List<Ocena> getOcena() {
		return ocena;
	}

	public void setOcena(List<Ocena> ocena) {
		this.ocena = ocena;
	}
	
	public Odeljenje getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}
	

	
	
	

}
