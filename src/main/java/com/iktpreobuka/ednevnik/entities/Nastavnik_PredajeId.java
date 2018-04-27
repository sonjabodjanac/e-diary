package com.iktpreobuka.ednevnik.entities;

import java.io.Serializable;

public class Nastavnik_PredajeId implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer nid;
	private Integer pid;
	private Integer oid;
	
	public Nastavnik_PredajeId(Integer nid, Integer pid, Integer oid) {
		super();
		this.nid = nid;
		this.pid = pid;
		this.oid = oid;
		
	}

	public Nastavnik_PredajeId() {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
	
	
	
	
}
