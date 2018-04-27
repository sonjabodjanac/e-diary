package com.iktpreobuka.ednevnik.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.iktpreobuka.ednevnik.entities.Ucenik;

public interface UcenikRepository extends CrudRepository<Ucenik,Integer> {

    public List<Ucenik> findByJmbg(String jmbg);
    
}