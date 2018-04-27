package com.iktpreobuka.ednevnik.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.ednevnik.entities.Odeljenje;


@Service
public class OdeljenjeDaoImpl implements OdeljenjeDao {
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Odeljenje> findOdeljenjaByRazred(Integer razred_id){
		String sql = "select o " +
	                 "from com.iktpreobuka.ednevnik.entities.Odeljenje o " +
				     "inner join fetch o.razred r " +
	                 "where o.razred=r and r.id = :razred_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("razred_id", razred_id);
		
		List<Odeljenje> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Odeljenje> findOdeljenjaByNastavnik(Integer nastavnik_id){
		String sql = "select o " +
	                 "from com.iktpreobuka.ednevnik.entities.Odeljenje o, " +
				     "com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje np " +
	                 "where o.id=np.oid and np.nid = :nastavnik_id";
		
		Query query = em.createQuery(sql);
		query.setParameter("nastavnik_id", nastavnik_id);
		
		List<Odeljenje> result = new ArrayList<>();
		result = query.getResultList();
		return result;

		      }

}
