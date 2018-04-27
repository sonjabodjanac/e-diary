package com.iktpreobuka.ednevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje;
import com.iktpreobuka.ednevnik.entities.Nastavnik_PredajeId;

public interface Nastavnik_PredajeRepository extends CrudRepository<Nastavnik_Predaje,Nastavnik_PredajeId> {

	public Nastavnik_Predaje findByNid(Integer nId); 
}
