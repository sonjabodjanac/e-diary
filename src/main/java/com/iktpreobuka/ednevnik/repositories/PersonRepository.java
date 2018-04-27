package com.iktpreobuka.ednevnik.repositories;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ednevnik.entities.Osoba;

public interface PersonRepository extends CrudRepository<Osoba , Integer> {
	
	public Osoba findOsobaById(Integer id);
	

}
