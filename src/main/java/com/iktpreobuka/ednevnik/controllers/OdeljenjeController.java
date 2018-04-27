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
import com.iktpreobuka.ednevnik.dto.OdeljenjeDTO;
import com.iktpreobuka.ednevnik.entities.Odeljenje;
import com.iktpreobuka.ednevnik.entities.Razred;
import com.iktpreobuka.ednevnik.repositories.OdeljenjeRepository;
import com.iktpreobuka.ednevnik.repositories.RazredRepository;
import com.iktpreobuka.ednevnik.security.Views;
import com.iktpreobuka.ednevnik.services.OdeljenjeDao;

@RestController
@RequestMapping("/api/v1/classDepartmants")
public class OdeljenjeController {
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	private RazredRepository razredRepository;
	
	@Autowired
	private OdeljenjeDao odeljenjeDao;
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createOdeljenje(@RequestBody OdeljenjeDTO odeljenjeDto){
		try {
		Razred razred = razredRepository.findRazredByBrRazreda(odeljenjeDto.getBrRazreda());
		if(razred != null) {
	    Odeljenje odeljenje = new Odeljenje();
	    if(odeljenjeDto.getOznaka() != null && odeljenjeDto.getOznaka() != "") {
	    	if(odeljenjeDto.getOznaka().substring(0,1).equals(odeljenjeDto.getBrRazreda().toString())) {
	    		if(odeljenjeRepository.findByOznaka(odeljenjeDto.getOznaka()) == null) {
		odeljenje.setOznaka(odeljenjeDto.getOznaka());
		odeljenje.setRazred(razred);
		odeljenjeRepository.save(odeljenje);
		return new ResponseEntity<Odeljenje>(odeljenje,HttpStatus.OK) ;	
		}
	    		return new ResponseEntity<RestError>(new RestError(4, "Class departmant already exists."), HttpStatus.NOT_FOUND);
	    	}
	    	return new ResponseEntity<RestError>(new RestError(3, "This class departmant doesn't belong to this class."), HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<RestError>(new RestError(2, "Insert class departmant mark."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RestError>(new RestError(1, "Class doesn't exist."), HttpStatus.NOT_FOUND);
		}catch(Exception e){
			return new ResponseEntity<RestError>(new RestError(5, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@JsonView(Views.Admin.class)
    @CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllClassDepartmentsPublic() {
		try {
			Iterable<Odeljenje> odeljenja = odeljenjeRepository.findAll();
				return new ResponseEntity<Iterable<Odeljenje>>(odeljenja, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
    
    //metoda koja vraca listu odeljenja na osnovu nastavnika
    
    @CrossOrigin(origins="http://localhost:4200")
    @JsonView(Views.Public.class)
 	@RequestMapping(method = RequestMethod.GET,value="/teacher/{nId}")
 	public ResponseEntity<?> getAllClassDepartmentsByTeacher(@PathVariable Integer nId) {
 		try {
 			Iterable<Odeljenje> odeljenja = odeljenjeDao.findOdeljenjaByNastavnik(nId);
 			if(odeljenja != null) {
 				return new ResponseEntity<Iterable<Odeljenje>>(odeljenja, HttpStatus.OK);
 			}
 			return new ResponseEntity<RestError>(new RestError(1, "Class departmants doesn't found!"), HttpStatus.NOT_FOUND);
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
			Odeljenje odeljenje = odeljenjeRepository.findOne(id);
			if (odeljenjeRepository.exists(id)) {
				return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class department doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateClassDepartment(@PathVariable Integer id, @RequestBody Odeljenje o) {
		try {
			Odeljenje odeljenje = odeljenjeRepository.findOne(id);
			if (odeljenje != null) {
				odeljenje.setOznaka(o.getOznaka());
				odeljenjeRepository.save(odeljenje);
				return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class department doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteClassDepartment(@PathVariable Integer id) {
		try {
			Odeljenje odeljenje = odeljenjeRepository.findOne(id);
			if (odeljenje != null) {
				odeljenjeRepository.delete(odeljenje);
				return new ResponseEntity<Odeljenje>(odeljenje, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Class department doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	

}
