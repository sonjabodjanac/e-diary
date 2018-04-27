package com.iktpreobuka.ednevnik.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.ednevnik.entities.Ucenik;

@Service
public class UcenikDaoImpl implements UcenikDao {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ucenik> findUcenikeByOdeljenje(Integer odeljenje_id){
		String sql = "select u " +
	                 "from com.iktpreobuka.ednevnik.entities.Ucenik u " +
				     "inner join fetch u.odeljenje o " +
	                 "where o.id = :odeljenje_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("odeljenje_id", odeljenje_id);
		
		List<Ucenik> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ucenik> findUcenikeByRoditelj(Integer roditelj_id){
		String sql = "select u " +
	                 "from com.iktpreobuka.ednevnik.entities.Ucenik u " +
				     "inner join fetch u.roditelj r " +
	                 "where r.id = :roditelj_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("roditelj_id", roditelj_id);
		
		List<Ucenik> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ucenik> findUcenikeByNastavnikAndPredmet(Integer nastavnik_id,Integer predmet_id){
		String sql = "select u " +
	                 "from com.iktpreobuka.ednevnik.entities.Ucenik u, " +
				     "com.iktpreobuka.ednevnik.entities.Odeljenje o, " +
				     "com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje np " +
	                 "where u.odeljenje=o and o.id=np.oid and np.nid=:nastavnik_id and np.pid=:predmet_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("nastavnik_id", nastavnik_id);
		query.setParameter("predmet_id", predmet_id);
		
		List<Ucenik> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }

	}

	


