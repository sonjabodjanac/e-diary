package com.iktpreobuka.ednevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ednevnik.entities.Roditelj;

public interface RoditeljRepository extends CrudRepository<Roditelj,Integer> {
	
	public List<Roditelj> findByJmbg(String jmbg);
	
	public Roditelj findByImeAndPrezime(String ime,String prezime);

}
