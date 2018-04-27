package com.iktpreobuka.ednevnik.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iktpreobuka.ednevnik.enums.TipOsobe;
import com.iktpreobuka.ednevnik.security.Views;
import com.iktpreobuka.ednevnik.serialization.CustomDateDeserializer;
import com.iktpreobuka.ednevnik.serialization.CustomDateSerializer;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Osoba {

	
    @Id
    @GenericGenerator(
        name = "table", 
        strategy = "enhanced-table", 
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "table_name", 
                value = "sequence_table"
            )
    })
    @JsonView(Views.Public.class)
    @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
	protected Integer id;
    
    
    @JsonView(Views.Public.class)
    @NotNull
	protected String ime;
    @JsonView(Views.Public.class)
    @NotNull
	protected String prezime;
    @JsonView(Views.Private.class)
    @NotNull
	protected String jmbg;
    @JsonView(Views.Public.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
    pattern="dd-MM-yyyy")
    @JsonSerialize(using=CustomDateSerializer.class)
    @NotNull
	protected Date datumRodjenja;
	@Enumerated(EnumType.ORDINAL)
	protected TipOsobe tipOsobe;
    @JsonView(Views.Admin.class)
	@Version
	protected Integer verzija;

	@JsonView(Views.Admin.class)
	@JsonBackReference
	@OneToOne(mappedBy = "osoba", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected Korisnik korisnik;

	public Osoba() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Osoba(Integer id, String ime, String prezime, String jmbg, Date datumRodjenja, TipOsobe tipOsobe,
			Integer verzija) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.verzija = verzija;
		this.datumRodjenja = datumRodjenja;
		this.tipOsobe = tipOsobe;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}
	 @JsonDeserialize(using=CustomDateDeserializer.class)
	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public TipOsobe getTipOsobe() {
		return tipOsobe;
	}

	public void setTipOsobe(TipOsobe tipOsobe) {
		this.tipOsobe = tipOsobe;
	}

}
