package com.iktpreobuka.ednevnik.services;

import java.util.List;

import com.iktpreobuka.ednevnik.entities.Ocena;

public interface OcenaDao {

	List<Ocena> findOceneByPredmetAndUcenik(Integer predmet_id, Integer ucenik_id);

	List<Ocena> findOceneByUcenik(Integer ucenik_id);


}
