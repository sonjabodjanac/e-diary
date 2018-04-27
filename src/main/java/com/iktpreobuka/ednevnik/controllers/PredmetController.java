package com.iktpreobuka.ednevnik.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.controllers.util.RestError;
import com.iktpreobuka.ednevnik.entities.Predmet;
import com.iktpreobuka.ednevnik.repositories.PredmetRepository;
import com.iktpreobuka.ednevnik.security.Views;
import com.iktpreobuka.ednevnik.services.PredmetDao;

@RestController
@RequestMapping("/api/v1/subjects")
public class PredmetController {
	
	@Autowired
	private PredmetRepository predmetRepository;
	
	@Autowired
	private PredmetDao predmetDao;
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createSubject(@RequestBody Predmet p){
		
		try {
	    Predmet predmet = new Predmet();
	    if(p.getFond() != null && p.getNaziv() != null && p.getNaziv() != "") {
	    	if(predmetRepository.findByNaziv(p.getNaziv()) == null) {
		predmet.setFond(p.getFond());
		predmet.setNaziv(p.getNaziv());
		predmetRepository.save(predmet);
		return new ResponseEntity<Predmet>(predmet,HttpStatus.OK);
	    	}
	    	return new ResponseEntity<RestError>(new RestError(2, "Subject already exist! Duplicate entry!"),
					HttpStatus.NOT_FOUND);
	    }
		return new ResponseEntity<RestError>(new RestError(1, "Insert all data."),HttpStatus.NOT_FOUND);
		}catch(Exception e){
			return new ResponseEntity<RestError>(new RestError(3, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    @CrossOrigin(origins="http://localhost:4200")
    @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllSubjects() {
		try {
			Iterable<Predmet> predmeti = predmetRepository.findAll();
				return new ResponseEntity<Iterable<Predmet>>(predmeti, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    //metoda koja vraca listu predmeta na osnovu odeljenja i nastavnika
    
    @CrossOrigin(origins="http://localhost:4200")
    @JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,value="/classDepartmant/{oId}/teacher/{nId}")
	public ResponseEntity<?> getAllSubjectsByTeacherAndClassDep(@PathVariable Integer oId,@PathVariable Integer nId) {
		try {
			Iterable<Predmet> predmeti = predmetDao.findPredmeteByOdeljenjeAndNastavnik(oId, nId);
			if(predmeti != null){
				return new ResponseEntity<Iterable<Predmet>>(predmeti, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subjects doesn't exists."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    
    
	@CrossOrigin(origins="http://localhost:4200")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getSubjectById(@PathVariable Integer id) {
		try {
			Predmet predmet = predmetRepository.findOne(id);
			if (predmetRepository.exists(id)) {
				return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subject doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	//metoda koja vraca listu predmeta na osnovu odeljenja 

	@CrossOrigin(origins="http://localhost:4200")
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/classDepartmant/{oId}")
	public ResponseEntity<?> getSubjectsByClassDepartmant(@PathVariable Integer oId) {
		try {
			List<Predmet> predmeti = predmetDao.findPredmeteByOdeljenje(oId);
			if (!predmeti.isEmpty()) {
				return new ResponseEntity <List<Predmet>>(predmeti, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subjects doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	// metoda vraca listu predmeta za odredjenog ucenika
	
	@CrossOrigin(origins="http://localhost:4200")
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/find-by-student/{ucenikId}")
	public ResponseEntity<?> getSubjectsByStudent(@PathVariable Integer ucenikId) {
		try {
			List<Predmet> predmeti = predmetDao.findPredmeteByUcenik(ucenikId);
			if (predmeti != null) {
				return new ResponseEntity <List<Predmet>>(predmeti, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subject doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateClass(@PathVariable Integer id, @RequestBody Predmet p) {
		try {
			Predmet predmet = predmetRepository.findOne(id);
			if (predmet != null) {
				predmet.setFond(p.getFond());
				predmet.setNaziv(p.getNaziv());
				predmetRepository.save(predmet);
				return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subject doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Integer id) {
		try {
			Predmet predmet = predmetRepository.findOne(id);
			if (predmet != null) {
				predmetRepository.delete(predmet);
				return new ResponseEntity<Predmet>(predmet, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Subject doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
