package com.iktpreobuka.ednevnik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.ednevnik.entities.Razred;

@Repository
public interface RazredRepository extends CrudRepository<Razred,Integer> {

	public Razred findRazredByBrRazreda(Integer brRazreda);
}
