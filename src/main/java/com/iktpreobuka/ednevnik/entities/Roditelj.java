package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.enums.TipOsobe;
import com.iktpreobuka.ednevnik.security.Views;

@Entity(name="roditelj")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Roditelj extends Osoba {
	
	@JsonView(Views.Admin.class)
	@JsonBackReference(value = "roditelj")
	@OneToMany(mappedBy = "roditelj",fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
	private List<Ucenik> ucenici = new ArrayList<Ucenik>();
	

	public Roditelj() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
	public Roditelj(Integer id, String ime, String prezime, String jmbg,Date datumRodjenja, TipOsobe tipOsobe, Integer verzija) {
		super(id, ime, prezime, jmbg,datumRodjenja,tipOsobe, verzija);
		// TODO Auto-generated constructor stub
	}


	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

	

	
	
	
}
