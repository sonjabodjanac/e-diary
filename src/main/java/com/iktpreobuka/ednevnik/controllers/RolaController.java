package com.iktpreobuka.ednevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.controllers.util.RestError;
import com.iktpreobuka.ednevnik.entities.Korisnik;
import com.iktpreobuka.ednevnik.entities.RoleEntity;
import com.iktpreobuka.ednevnik.repositories.RoleRepository;
import com.iktpreobuka.ednevnik.security.Views;

@RestController
@RequestMapping("/api/v1/roles")
public class RolaController {
	
	@Autowired
	private RoleRepository roleRepository;

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRoles() {
		try {
			Iterable<RoleEntity> role = roleRepository.findAll();
			return new ResponseEntity<Iterable<RoleEntity>>(role, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET,value="/{id}")
	public ResponseEntity<?> getRoleById(@PathVariable Integer id) {
		try {
		      RoleEntity rola = roleRepository.findOne(id);
		      if(rola != null) {
				return new ResponseEntity<RoleEntity>(rola, HttpStatus.OK);
		      }
		      return new ResponseEntity<RestError>(new RestError(1, "Role doesn't found."), HttpStatus.NOT_FOUND); 
		      
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@RequestBody RoleEntity r) {
		try {
		      RoleEntity rola = new RoleEntity();
		      rola.setId(r.getId());
		      rola.setIme(r.getIme());
		      roleRepository.save(rola);
				return new ResponseEntity<RoleEntity>(rola, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT,value="/{id}")
	public ResponseEntity<?> updateRole(@PathVariable Integer id,@RequestBody RoleEntity r) {
		try {
		      RoleEntity rola = roleRepository.findOne(id);
		      if(rola != null) {
		      rola.setIme(r.getIme());
		      roleRepository.save(rola);
				return new ResponseEntity<RoleEntity>(rola, HttpStatus.OK);
		      }
		      return new ResponseEntity<RestError>(new RestError(1, "Role doesn't found."), HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(method = RequestMethod.DELETE,value="/{id}")
	public void deleteRole(@PathVariable Integer id) {
		
		      RoleEntity rola = roleRepository.findOne(id);
		      if(rola != null) {
		      roleRepository.delete(rola);
	}
	
	}
}
