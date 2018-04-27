package com.iktpreobuka.ednevnik.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OdeljenjeDTO {
	
	private Integer id;
	private String oznaka;
	private Integer verzija;
	private Integer brRazreda;

	public OdeljenjeDTO() {
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
	@JsonProperty("oznaka")
	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
	@JsonProperty("verzija")
	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}
	public Integer getBrRazreda() {
		return brRazreda;
	}


	public void setBrRazreda(Integer brRazreda) {
		this.brRazreda = brRazreda;
	}

	
	
	
	
	
	
	

}
