package com.iktpreobuka.ednevnik.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.ednevnik.entities.Predmet;

@Service
public class PredmetDaoImpl implements PredmetDao {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Predmet> findPredmeteByOdeljenje(Integer odeljenje_id){
		String sql = "select p " +
	                 "from com.iktpreobuka.ednevnik.entities.Predmet p, " +
				     " com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje np " +
	                 "where p.id = np.pid and np.oid=:odeljenje_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("odeljenje_id", odeljenje_id);
		
		List<Predmet> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Predmet> findPredmeteByOdeljenjeAndNastavnik(Integer odeljenje_id,Integer nastavnik_id){
		String sql = "select p " +
	                 "from com.iktpreobuka.ednevnik.entities.Predmet p, " +
				     " com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje np " +
	                 "where p.id = np.pid and np.oid=:odeljenje_id and np.nid=:nastavnik_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("odeljenje_id", odeljenje_id);
		query.setParameter("nastavnik_id", nastavnik_id);
		
		List<Predmet> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Predmet> findPredmeteByUcenik(Integer ucenik_id){
		String sql = "select p " +
				"from com.iktpreobuka.ednevnik.entities.Predmet p, " +
				"com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje np, " +
                "com.iktpreobuka.ednevnik.entities.Ucenik u, " +
			    "com.iktpreobuka.ednevnik.entities.Odeljenje o " +
                "where p.id=np.pid and np.oid=o.id and o=u.odeljenje and u.id=:ucenik_id ";
		
		Query query = em.createQuery(sql);
		query.setParameter("ucenik_id", ucenik_id);
		
		List<Predmet> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	
	
	

}
