package com.iktpreobuka.ednevnik.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iktpreobuka.ednevnik.serialization.CustomDateDeserializer;
import com.iktpreobuka.ednevnik.serialization.CustomDateSerializer;


public class UcenikDTO {
	
	private Integer id;
	private String ime;
	private String prezime;
	private String jmbg;
	 @JsonSerialize(using=CustomDateSerializer.class)
	private Date datumRodjenja;
	private String oznaka;
	private String rIme;
	private String rPrezime;
	
	public UcenikDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JsonProperty("ime")
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
	@JsonProperty("prezime")
	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	@JsonProperty("jmbg")
	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	@JsonProperty("oznaka")
	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	@JsonProperty("rIme")
	public String getrIme() {
		return rIme;
	}

	public void setrIme(String rIme) {
		this.rIme = rIme;
	}
	@JsonProperty("rPrezime")
	public String getrPrezime() {
		return rPrezime;
	}

	public void setrPrezime(String rPrezime) {
		this.rPrezime = rPrezime;
	}
	@JsonDeserialize(using=CustomDateDeserializer.class)
	@JsonProperty("datumRodjenja")
	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

    
	
	
	
	

}
