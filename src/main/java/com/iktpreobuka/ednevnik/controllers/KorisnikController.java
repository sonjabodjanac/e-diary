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
import com.iktpreobuka.ednevnik.dto.KorisnikDTO;
import com.iktpreobuka.ednevnik.entities.Korisnik;
import com.iktpreobuka.ednevnik.entities.Osoba;
import com.iktpreobuka.ednevnik.entities.Nastavnik;
import com.iktpreobuka.ednevnik.entities.RoleEntity;
import com.iktpreobuka.ednevnik.repositories.KorisnikRepository;
import com.iktpreobuka.ednevnik.repositories.NastavnikRepository;
import com.iktpreobuka.ednevnik.repositories.PersonRepository;
import com.iktpreobuka.ednevnik.repositories.RoleRepository;
import com.iktpreobuka.ednevnik.security.Views;

@RestController
@RequestMapping("/api/v1/users")
public class KorisnikController {
	
	@Autowired 
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers() {
		try {
			Iterable<Korisnik> korisnici = korisnikRepository.findAll();
			return new ResponseEntity<Iterable<Korisnik>>(korisnici, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		try {
			Korisnik korisnik = korisnikRepository.findOne(id);
			if (korisnikRepository.exists(id)) {
				return new ResponseEntity<Korisnik>(korisnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "User not found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	@RequestMapping(method = RequestMethod.POST,value="/role/{rId}/person/{oId}")
	public ResponseEntity<?> createUser(@PathVariable Integer rId,@PathVariable Integer oId,
			@RequestBody KorisnikDTO k) {
		try {
			RoleEntity rola = roleRepository.findOne(rId);
			Osoba osoba = personRepository.findOne(oId);
			if (osoba != null) {
				Korisnik korisnik = new Korisnik();
				korisnik.setId(k.getId());
				korisnik.setEmail(k.getEmail());
				korisnik.setPassword(k.getPassword());
				korisnik.setUsername(k.getUsername());
				korisnik.setRola(rola);
				korisnik.setOsoba(osoba);
				korisnikRepository.save(korisnik);
				return new ResponseEntity<Korisnik>(korisnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "User didn't saved."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody KorisnikDTO k) {
		try {
			Korisnik korisnik = korisnikRepository.findOne(id);
			if (korisnik != null) {
				korisnik.setEmail(k.getEmail());
				korisnik.setPassword(k.getPassword());
				korisnik.setUsername(k.getUsername());
				korisnikRepository.save(korisnik);
				return new ResponseEntity<Korisnik>(korisnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "User doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		try {
			Korisnik korisnik = korisnikRepository.findOne(id);
			if (korisnik != null) {
				korisnikRepository.delete(korisnik);
				return new ResponseEntity<Korisnik>(korisnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "User doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
