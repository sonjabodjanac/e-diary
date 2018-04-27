package com.iktpreobuka.ednevnik.repositories;


import org.springframework.data.repository.CrudRepository;
import com.iktpreobuka.ednevnik.entities.Predmet;

public interface PredmetRepository extends CrudRepository<Predmet, Integer> {

	public Predmet findByNaziv(String naziv);


}
