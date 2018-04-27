package com.iktpreobuka.ednevnik.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.security.Views;

@Entity
@Table(name = "korisnik")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Korisnik {
	
	@JsonView(Views.Admin.class)
	@Id
	@GeneratedValue
	private Integer id; 
	@JsonView(Views.Admin.class)
	private String email;
	@JsonView(Views.Admin.class)
	private String username;
	@JsonIgnore
	@JsonView(Views.Admin.class)
	private String password;
	
	//@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn
	private RoleEntity rola;
	
	@JsonView(Views.Admin.class)
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) 
	@JoinColumn(name = "osoba")
	private Osoba osoba;
	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	


	public RoleEntity getRola() {
		return rola;
	}


	public void setRola(RoleEntity rola) {
		this.rola = rola;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Osoba getOsoba() {
		return osoba;
	}


	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getVerzija() {
		return verzija;
	}


	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}


	
    
	
	

}
