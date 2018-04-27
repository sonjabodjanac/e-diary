package com.iktpreobuka.ednevnik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.ednevnik.entities.Ocena;
import com.iktpreobuka.ednevnik.entities.Predmet;
import com.iktpreobuka.ednevnik.enums.TipOcene;
import com.iktpreobuka.ednevnik.entities.Ucenik;

public interface OcenaRepository extends CrudRepository<Ocena,Integer> {
	
	public List<Ocena> findOcenaByUcenik(Ucenik ucenik);
	
	public Ocena findByTipOceneAndUcenikAndPredmet(TipOcene tipOcene,Ucenik ucenik,Predmet predmet);
	
	public List<Ocena> findByUcenikAndPredmet(Integer ucenikId,Integer predmetId);
	
	
	 @Query(value = "select count(vrednost) "
			 + "from ocena where predmet_id=? and ucenik_id=? and deleted <>1; ",nativeQuery = true
	    )
	    Integer countByVrednost(@Param("predmet_id") Integer predmet_id,@Param("ucenik_id") Integer ucenik_id);

	 
//	 @Query(value = "select round(avg(vrednost)) "
//			 + "from ocena where predmet_id=? and ucenik_id=? and deleted <>1; ",nativeQuery = true
//	    )
//	    Integer avgGradeBySecondSemester(@Param("predmet_id") Integer predmet_id,@Param("ucenik_id") Integer ucenik_id);
//
//	 @Query(value = "select round(avg(vrednost)) "
//			 + "from ocena where predmet_id=? and ucenik_id=? and deleted <>1 and polugodiste=1; ",nativeQuery = true
//	    )
//	    Integer avgGradeByFirstSemester(@Param("predmet_id") Integer predmet_id,@Param("ucenik_id") Integer ucenik_id);

	 

}
