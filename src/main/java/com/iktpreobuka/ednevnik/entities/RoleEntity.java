package com.iktpreobuka.ednevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.security.Views;
@Entity
@Table(name="role")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RoleEntity {
	
	@JsonView(Views.Admin.class)
	@Id
	@GeneratedValue
	private Integer id;
	@JsonView(Views.Admin.class)
	private String ime;
	
	@JsonBackReference
	@OneToMany(mappedBy = "rola",fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
	private List<Korisnik> korisnici =  new ArrayList<>();
	
	@Version
	private Integer verzija;

	public RoleEntity() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}
	
	
}
