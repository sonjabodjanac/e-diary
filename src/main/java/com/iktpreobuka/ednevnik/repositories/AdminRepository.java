package com.iktpreobuka.ednevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ednevnik.entities.Admin;

public interface AdminRepository extends CrudRepository<Admin,Integer> {
 
	public List<Admin> findByJmbg(String Jmbg);
}
