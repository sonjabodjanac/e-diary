package com.iktpreobuka.ednevnik.controllers;

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
import com.iktpreobuka.ednevnik.entities.Razred;
import com.iktpreobuka.ednevnik.repositories.RazredRepository;
import com.iktpreobuka.ednevnik.security.Views;

@RestController
@RequestMapping("/api/v1/classes")
public class RazredController {
	
	
	@Autowired
	private RazredRepository razredRepository;
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createClass(@RequestBody Razred r){
		
		try {
	    Razred razred = new Razred();
	    if(r.getBrRazreda() != null && r.getSkGodina() != null && r.getSkGodina() != "") {
	    	if(razredRepository.findRazredByBrRazreda(r.getBrRazreda()) == null) {
		razred.setBrRazreda(r.getBrRazreda());
		razred.setSkGodina(r.getSkGodina());
		razredRepository.save(razred);
		return new ResponseEntity<Razred>(razred,HttpStatus.OK);
		  }
	    	return new ResponseEntity<RestError>(new RestError(2, "Class alredy exist. Duplicate entry." ),HttpStatus.NOT_FOUND);
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
	public ResponseEntity<?> getAllClassesAdmin() {
		try {
			Iterable<Razred> razredi = razredRepository.findAll();
			if(razredi != null) {
				return new ResponseEntity<Iterable<Razred>>(razredi, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Classes doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
    @JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getClassDepartmentById(@PathVariable Integer id) {
		try {
			Razred razred = razredRepository.findOne(id);
			if (razredRepository.exists(id)) {
				return new ResponseEntity<Razred>(razred, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateClass(@PathVariable Integer id, @RequestBody Razred r) {
		try {
			Razred razred = razredRepository.findOne(id);
			if (razred != null) {
				razred.setBrRazreda(r.getBrRazreda());
				razred.setSkGodina(r.getSkGodina());
				razredRepository.save(razred);
				return new ResponseEntity<Razred>(razred, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class department doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteClass(@PathVariable Integer id) {
		try {
			Razred razred = razredRepository.findOne(id);
			if (razred != null) {
				razredRepository.delete(razred);
				return new ResponseEntity<Razred>(razred, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
