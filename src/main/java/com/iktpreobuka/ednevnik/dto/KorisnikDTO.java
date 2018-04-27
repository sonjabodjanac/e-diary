package com.iktpreobuka.ednevnik.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KorisnikDTO {
	
	private Integer id;
	private String email;
	private String password;
	private String username;
	private Integer verzija;
	
	public KorisnikDTO() {
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

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("verzija")
	public Integer getVerzija() {
		return verzija;
	}

	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}

	
	
	
	
	

}
