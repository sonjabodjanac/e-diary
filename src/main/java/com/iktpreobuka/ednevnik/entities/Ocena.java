package com.iktpreobuka.ednevnik.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.enums.TipOcene;
import com.iktpreobuka.ednevnik.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="ocena")
@SQLDelete(sql="UPDATE ocena SET deleted = '1' WHERE id = ?")
@Where(clause="deleted <> '1'")
public class Ocena {
	
	@JsonView(Views.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@JsonView(Views.Public.class)
	@Enumerated(EnumType.ORDINAL)
    private TipOcene tipOcene;
	
	@JsonView(Views.Public.class)
	private Date datumUnosa;
	
	@JsonView(Views.Public.class)
	private Integer vrednost;
	
	@JsonView(Views.Public.class)
	private Integer polugodiste;

	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	
	@JsonView(Views.Admin.class)
	private Boolean deleted;
	
	@JsonView(Views.Admin.class)
	//@JsonManagedReference(value = "ocena_ucenik")
    @ManyToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
	@JoinColumn
	private Ucenik ucenik;
	
	@JsonView(Views.Admin.class)
	//@JsonManagedReference(value = "ocena_predmet")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn
	private Predmet predmet;
	
	@JsonView(Views.Admin.class)
	//@JsonManagedReference(value = "ocena_nastavnik")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn
	private Nastavnik nastavnik;
	
	
	public Ocena() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Enumerated(EnumType.STRING)
	public TipOcene getTipOcene() {
		return tipOcene;
	}



	public void setTipOcene(TipOcene tipOcene) {
		this.tipOcene = tipOcene;
	}

	@JsonFormat(
	shape = JsonFormat.Shape.STRING,
	pattern = "dd-MM-yyyy")
	public Date getDatumUnosa() {
		return this.datumUnosa;
	}


	public void setDatumUnosa(Date datumUnosa) {
		this.datumUnosa = datumUnosa;
	}


	public Ucenik getUcenik() {
		return ucenik;
	}


	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Predmet getPredmet() {
		return predmet;
	}


	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(Nastavnik nastavnik) {
		this.nastavnik = nastavnik;
	}
	public Integer getVrednost() {
		return vrednost;
	}
	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}
	public Integer getPolugodiste() {
		return polugodiste;
	}
	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}
	public Integer getVerzija() {
		return verzija;
	}
	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
	


	
	
	

}
