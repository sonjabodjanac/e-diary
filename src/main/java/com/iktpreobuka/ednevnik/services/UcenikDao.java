package com.iktpreobuka.ednevnik.services;

import java.util.List;

import com.iktpreobuka.ednevnik.entities.Ucenik;

public interface UcenikDao {

	List<Ucenik> findUcenikeByOdeljenje(Integer odeljenje_id);

	List<Ucenik> findUcenikeByRoditelj(Integer roditelj_id);

	List<Ucenik> findUcenikeByNastavnikAndPredmet(Integer nastavnik_id, Integer predmet_id);

}
