package com.iktpreobuka.ednevnik.services;

import java.util.List;

import com.iktpreobuka.ednevnik.entities.Predmet;

public interface PredmetDao {

	List<Predmet> findPredmeteByOdeljenje(Integer odeljenje_id);

	List<Predmet> findPredmeteByOdeljenjeAndNastavnik(Integer odeljenje_id, Integer nastavnik_id);

	List<Predmet> findPredmeteByUcenik(Integer ucenik_id);

}
