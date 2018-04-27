package com.iktpreobuka.ednevnik.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iktpreobuka.ednevnik.enums.TipOcene;


public class OcenaDTO {
	
	private Integer id;
	private TipOcene tipOcene;
	private Integer vrednost;
//	private Date datumUnosa;
	private Integer polugodiste;
	private Boolean deleted;
	private Integer verzija;
	
	


	public OcenaDTO() {
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

	@JsonProperty("tipOcene")
	public TipOcene getTipOcene() {
		return tipOcene;
	}

	@Enumerated(EnumType.ORDINAL)
	public void setTipOcene(TipOcene tipOcene) {
		this.tipOcene = tipOcene;
	}
	@JsonProperty("vrednost")
	public Integer getVrednost() {
		return vrednost;
	}


	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}


	@JsonProperty("polugodiste")
	public Integer getPolugodiste() {
		return polugodiste;
	}


	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}

	@JsonProperty("verzija")
	public Integer getVerzija() {
		return verzija;
	}


	public void setVerzija(Integer verzija) {
		this.verzija = verzija;
	}
	@JsonProperty("deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

//	public Date getDatumUnosa() {
//		return datumUnosa;
//	}
//
//	public void setDatumUnosa(Date datumUnosa) {
//		this.datumUnosa = datumUnosa;
//	}

	
	
	
	

}
