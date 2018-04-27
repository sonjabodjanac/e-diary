package com.iktpreobuka.ednevnik.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.security.Views;

@Entity 
@Table(name="nastavnik_predaje")
@IdClass(Nastavnik_PredajeId.class)
public class Nastavnik_Predaje {
	
	@JsonView(Views.Admin.class)
	@Id
	private Integer nid;
	
	@JsonView(Views.Admin.class)
	@Id
	private Integer pid;
	
	@JsonView(Views.Admin.class)
	@Id
	private Integer oid;
	
	@JsonView(Views.Admin.class)
	@Version
	private Integer verzija;
	
	
	public Integer getVerzija() {
		return verzija;
	}


	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}


	public Nastavnik_Predaje() {
		super();
		// TODO Auto-generated constructor stub
	}
 

	public Integer getNid() {
		return nid;
	}


	public void setNid(Integer nid) {
		this.nid = nid;
	}


	public Integer getPid() {
		return pid;
	}


	public void setPid(Integer pid) {
		this.pid = pid;
	}


	public Integer getOid() {
		return oid;
	}


	public void setOid(Integer oid) {
		this.oid = oid;
	}



	
	
	

}
