package com.iktpreobuka.ednevnik.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ednevnik.entities.Ocena;


@Service 
public class OcenaDaoImpl implements OcenaDao {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ocena> findOceneByPredmetAndUcenik(Integer predmet_id,Integer ucenik_id){
		String sql = "select o " +
	                 "from com.iktpreobuka.ednevnik.entities.Ocena o, " +
    			     "com.iktpreobuka.ednevnik.entities.Predmet p, " +
     			     "com.iktpreobuka.ednevnik.entities.Ucenik u " +
	                 "where o.predmet=p and o.ucenik=u and p.id=:predmet_id and u.id=:ucenik_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("predmet_id", predmet_id);
		query.setParameter("ucenik_id", ucenik_id);
		
		List<Ocena> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ocena> findOceneByUcenik(Integer ucenik_id){
		String sql = "select o " +
	                 "from com.iktpreobuka.ednevnik.entities.Ocena o, " +
     			     "com.iktpreobuka.ednevnik.entities.Ucenik u " +
	                 "where o.ucenik=u and u.id=:ucenik_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("ucenik_id", ucenik_id);
		
		List<Ocena> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	

}
