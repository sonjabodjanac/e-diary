package com.iktpreobuka.ednevnik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.ednevnik.entities.Nastavnik;
import com.iktpreobuka.ednevnik.entities.Ucenik;

public interface NastavnikRepository extends CrudRepository<Nastavnik,Integer> {

	public Nastavnik findByImeAndPrezime(String ime,String prezime);
	
	public List<Nastavnik> findByJmbg(String jmbg);
	
	 @Query(value = "select distinct(n.id) "
			 + "from nastavnik n, nastavnik_predaje np where n.id=np.nid and np.pid=?; ",nativeQuery = true
	    )
	    Integer getNastavnikByPredmet(@Param("predmet_id") Integer predmet_id);
	 
	 public  List<Nastavnik> findByPrezime(String lastname);
	 
}
