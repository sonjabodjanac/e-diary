package com.iktpreobuka.ednevnik.repositories;

import org.springframework.data.repository.CrudRepository;
import com.iktpreobuka.ednevnik.entities.Odeljenje;

public interface OdeljenjeRepository extends CrudRepository<Odeljenje,Integer>{
	
       public Odeljenje findByOznaka(String oznaka);
       
}
