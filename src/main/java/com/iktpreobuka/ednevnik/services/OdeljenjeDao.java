package com.iktpreobuka.ednevnik.services;

import java.util.List;

import com.iktpreobuka.ednevnik.entities.Odeljenje;

public interface OdeljenjeDao {

	List<Odeljenje> findOdeljenjaByRazred(Integer razred_id);

	List<Odeljenje> findOdeljenjaByNastavnik(Integer nastavnik_id);

}
