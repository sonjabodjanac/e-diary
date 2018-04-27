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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.controllers.util.RestError;
import com.iktpreobuka.ednevnik.dto.NastavnikPredajeDTO;
import com.iktpreobuka.ednevnik.dto.UcenikDTO;
import com.iktpreobuka.ednevnik.entities.Admin;
import com.iktpreobuka.ednevnik.entities.Nastavnik;
import com.iktpreobuka.ednevnik.entities.Nastavnik_Predaje;

import com.iktpreobuka.ednevnik.entities.Odeljenje;
import com.iktpreobuka.ednevnik.entities.Predmet;
import com.iktpreobuka.ednevnik.entities.Roditelj;
import com.iktpreobuka.ednevnik.entities.Ucenik;
import com.iktpreobuka.ednevnik.enums.TipOsobe;
import com.iktpreobuka.ednevnik.repositories.AdminRepository;
import com.iktpreobuka.ednevnik.repositories.NastavnikRepository;
import com.iktpreobuka.ednevnik.repositories.Nastavnik_PredajeRepository;

import com.iktpreobuka.ednevnik.repositories.OdeljenjeRepository;
import com.iktpreobuka.ednevnik.repositories.RoditeljRepository;
import com.iktpreobuka.ednevnik.repositories.UcenikRepository;

import com.iktpreobuka.ednevnik.repositories.PredmetRepository;
import com.iktpreobuka.ednevnik.security.Views;
import com.iktpreobuka.ednevnik.services.UcenikDao;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	@Autowired
	private NastavnikRepository nastavnikRepository;

	@Autowired
	private OdeljenjeRepository odeljenjeRepository;

	@Autowired
	private Nastavnik_PredajeRepository nastavnikPredajeRepository;

	@Autowired
	private UcenikRepository ucenikRepository;

	@Autowired
	private RoditeljRepository roditeljRepository;
	
	@Autowired
	private PredmetRepository predmetRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UcenikDao ucenikDao;


	/***************************************** nastavnik endpoint**************************************/

	@JsonView(Views.Admin.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/teachers")
	public ResponseEntity<?> getAllTeachersPublic() {
		try {
			Iterable<Nastavnik> nastavnici = nastavnikRepository.findAll();
			if(nastavnici != null) {
			return new ResponseEntity<Iterable<Nastavnik>>(nastavnici, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Teachers doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	    @JsonView(Views.Admin.class)
		@CrossOrigin(origins="http://localhost:4200")
		@RequestMapping(method = RequestMethod.GET, value = "/teachers/{id}")
		public ResponseEntity<?> getTeacherById(@PathVariable Integer id) {
			try {
				Nastavnik nastavnik = nastavnikRepository.findOne(id);
				if (nastavnikRepository.exists(id)) {
					return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);
				}
				return new ResponseEntity<RestError>(new RestError(1, "Teacher doesn't found."), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	   
		//metoda za pretragu nastavnika po prezimenu
	    
		@JsonView(Views.Admin.class)
		@CrossOrigin(origins="http://localhost:4200")
		@RequestMapping(method = RequestMethod.GET, value = "/teachers/find-by-lastName")
		public ResponseEntity<?> getTeachersByLastName(@RequestParam String prezime) {
			try {
				Iterable<Nastavnik> nastavnici = nastavnikRepository.findByPrezime(prezime);
				if (nastavnici != null ) {
					return new ResponseEntity<Iterable<Nastavnik>>(nastavnici, HttpStatus.OK);
				}
				return new ResponseEntity<RestError>(new RestError(1, "Teacher doesn't found."), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		//metoda za pretragu nastavnika po jmbg-u
		
		@JsonView(Views.Private.class)
		@CrossOrigin(origins="http://localhost:4200")
		@RequestMapping(method = RequestMethod.GET, value = "/teachers/find-by-jmbg")
		public ResponseEntity<?> getTeacherByJmbg(@RequestParam String jmbg) {
			try {
				Iterable<Nastavnik> nastavnici = nastavnikRepository.findByJmbg(jmbg);
				if (nastavnici != null) {
					return new ResponseEntity<Iterable<Nastavnik>>(nastavnici, HttpStatus.OK);
				}
				return new ResponseEntity<RestError>(new RestError(1, "Teacher doesn't found."), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/teachers")
	public ResponseEntity<?> createTeacher(@RequestBody Nastavnik n) {
		try {
			Nastavnik nastavnik = new Nastavnik();
			if((n.getIme() != null && n.getPrezime() != null) && (n.getJmbg() != null && n.getDatumRodjenja() != null)){
				if(n.getIme() != "" && n.getPrezime() != "" && n.getJmbg() != ""){
			nastavnik.setIme(n.getIme());
			nastavnik.setPrezime(n.getPrezime());
			if(n.getJmbg().length() == 13 ) {
				if(nastavnikRepository.findByJmbg(n.getJmbg()).isEmpty()) {
			nastavnik.setJmbg(n.getJmbg());
			nastavnik.setDatumRodjenja(n.getDatumRodjenja());
	        nastavnik.setTipOsobe(TipOsobe.NASTAVNIK);
			nastavnikRepository.save(nastavnik);
			 return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);
			}
				return new ResponseEntity<RestError>(new RestError(4, "Teacher didn't saved. Duplicate entry!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(3, "Teacher didn't saved. Jmbg must had 13 digites!"), HttpStatus.NOT_FOUND);
				}
				return new ResponseEntity<RestError>(new RestError(2, "Teacher didn't saved. Fill all fields!"), HttpStatus.NOT_FOUND);	
			}
			return new ResponseEntity<RestError>(new RestError(1, "Teacher didn't saved. Insert all data!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(5, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/teachers/{id}")
	public ResponseEntity<?> updateTeacher(@PathVariable Integer id, @RequestBody Nastavnik n) {
		try {
			Nastavnik nastavnik = nastavnikRepository.findOne(id);
			if (nastavnik != null) {
				nastavnik.setIme(n.getIme());
				nastavnik.setPrezime(n.getPrezime());
				nastavnik.setJmbg(n.getJmbg());
				nastavnik.setDatumRodjenja(n.getDatumRodjenja());
				nastavnik.setTipOsobe(TipOsobe.NASTAVNIK);
				nastavnikRepository.save(nastavnik);
				return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Teacher doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/teachers/{id}")
	public ResponseEntity<?> deleteNastavnik(@PathVariable Integer id) {
		try {
			Nastavnik nastavnik = nastavnikRepository.findOne(id);
			if (nastavnik != null) {
				nastavnikRepository.delete(nastavnik);
				return new ResponseEntity<Nastavnik>(nastavnik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Teacher doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	/******************* endpoint za veznu tabelu Nastavnik_Predaje ***************************/
	
	
	@RequestMapping(method = RequestMethod.GET,value="/teachers/nastavnik_predaje")
	public ResponseEntity<?> getNastavnikPredaje(){
		try {
			Iterable<Nastavnik_Predaje> nastavnici_predaju = nastavnikPredajeRepository.findAll();
			if (nastavnici_predaju != null) {
				return new ResponseEntity<Iterable<Nastavnik_Predaje>>(nastavnici_predaju, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Teacher teach table is empty."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/teachers/nastavnik_predaje/{nId}")
	public ResponseEntity<?> createNastavnikPredaje(@PathVariable Integer nId,@RequestBody NastavnikPredajeDTO nP) {
		try {
		Nastavnik_Predaje nastavnik_pred = new Nastavnik_Predaje();
		Odeljenje odeljenje = odeljenjeRepository.findByOznaka(nP.getOznaka());
		Predmet predmet = predmetRepository.findByNaziv(nP.getNaziv());
		if(nId != null && odeljenje != null && predmet != null) {
		nastavnik_pred.setNid(nId);
		nastavnik_pred.setPid(predmet.getId());
		nastavnik_pred.setOid(odeljenje.getId());
		nastavnikPredajeRepository.save(nastavnik_pred);
		return new ResponseEntity<Nastavnik_Predaje>(nastavnik_pred, HttpStatus.OK);
		}
		return new ResponseEntity<RestError>(new RestError(1, "Insert all data."), HttpStatus.NOT_FOUND);
	} catch (Exception e) {
		return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	
	@RequestMapping(method = RequestMethod.DELETE, value = "/teachers/nastavnik_predaje/{nId}")
	public void deleteNastavnik_Predaje(@PathVariable Integer nId) {
		Nastavnik_Predaje nast_pred = nastavnikPredajeRepository.findByNid(nId);
		nastavnikPredajeRepository.delete(nast_pred);
	}

	/************************************ucenik endpoint************************************/

	@JsonView(Views.Admin.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/students")
	public ResponseEntity<?> getAllStudentsAdmin() {
		try {
			Iterable<Ucenik> ucenici = ucenikRepository.findAll();
			return new ResponseEntity<Iterable<Ucenik>>(ucenici, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	@JsonView(Views.Admin.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/students/{id}")
	public ResponseEntity<?> getStudentByIdAdmin(@PathVariable Integer id) {
		try {
			Ucenik ucenik = ucenikRepository.findOne(id);
			if (ucenikRepository.exists(id)) {
				return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Student doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//metoda za pretraga ucenika po jmbg-u
	
	@CrossOrigin(origins="http://localhost:4200")
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/students/find-by-jmbg")
	public ResponseEntity<?> getStudentByJmbg(@RequestParam String jmbg) {
		try {
			Iterable<Ucenik> ucenici = ucenikRepository.findByJmbg(jmbg);
			if (ucenici != null) {
				return new ResponseEntity<Iterable<Ucenik>>(ucenici, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Student doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


	//metoda vraca listu ucenika na osnovu predmeta i nastavnika
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/students/teacher/{nId}/subject/{pId}")
	public ResponseEntity<?> getStudentsByTeacherAndSubjects(@PathVariable Integer nId,@PathVariable Integer pId) {
		try {
			List<Ucenik> ucenici = ucenikDao.findUcenikeByNastavnikAndPredmet(nId,pId);
			if (!ucenici.isEmpty()) {
				return new ResponseEntity<List<Ucenik>>(ucenici, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Students doesn't exists."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//metoda koja vraca listu ucenika na osnovu roditelja
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/students/parent/{rId}")
	public ResponseEntity<?> getStudentsByParent(@PathVariable Integer rId) {
		try {
			List<Ucenik> ucenici = ucenikDao.findUcenikeByRoditelj(rId);
			if (!ucenici.isEmpty()) {
				return new ResponseEntity<List<Ucenik>>(ucenici, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Students doesn't exists."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//metoda koja vraca listu ucenika na osnovu odeljenja
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/students/classDepartmant/{oId}")
	public ResponseEntity<?> getStudentsByClassDepartmant(@PathVariable Integer oId) {
		try {
			List<Ucenik> ucenici = ucenikDao.findUcenikeByOdeljenje(oId);
			if (!ucenici.isEmpty()) {
				return new ResponseEntity<List<Ucenik>>(ucenici, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Students doesn't exists."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/students")
	public ResponseEntity<?> createStudent(@RequestBody UcenikDTO u) {
		try {
			Odeljenje odeljenje = odeljenjeRepository.findByOznaka(u.getOznaka());
			Roditelj roditelj = roditeljRepository.findByImeAndPrezime(u.getrIme(),u.getrPrezime());
			if(odeljenje != null && roditelj != null) {
			Ucenik ucenik = new Ucenik();
			if((u.getIme() != null && u.getPrezime() != null) && (u.getJmbg() != null && u.getDatumRodjenja() != null)){
				if(u.getIme() != "" && u.getPrezime() != "" && u.getJmbg() != ""){
			ucenik.setIme(u.getIme());
			ucenik.setPrezime(u.getPrezime());
			if(u.getJmbg().length() == 13 ) {
				if(ucenikRepository.findByJmbg(u.getJmbg()).isEmpty()) {
			ucenik.setJmbg(u.getJmbg());
			ucenik.setDatumRodjenja(u.getDatumRodjenja());
			ucenik.setTipOsobe(TipOsobe.UCENIK);
			ucenik.setOdeljenje(odeljenje);
			ucenik.setRoditelj(roditelj);
			ucenikRepository.save(ucenik);
				return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);
			}
				return new ResponseEntity<RestError>(new RestError(5, "Student didn't saved. Duplicate entry!"), HttpStatus.NOT_FOUND);
			}
				return new ResponseEntity<RestError>(new RestError(4, "Student didn't saved. Jmbg must had 13 digites!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(3, "Student didn't saved. Fill all fields!"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<RestError>(new RestError(2, "Student didn't saved. Insert all data!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Student didn't saved. Insert valid parent name and class departmant mark!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(6, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/students/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id,
			@RequestBody Ucenik u) {
		try {
			Ucenik ucenik = ucenikRepository.findOne(id);
			if (ucenik != null) {
			    ucenik.setIme(u.getIme());
				ucenik.setPrezime(u.getPrezime());
				ucenik.setJmbg(u.getJmbg());
				ucenik.setDatumRodjenja(u.getDatumRodjenja());
				ucenik.setTipOsobe(TipOsobe.UCENIK);
				ucenikRepository.save(ucenik);
				return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Student doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		try {
			Ucenik ucenik = ucenikRepository.findOne(id);
			if (ucenik != null) {
				ucenikRepository.delete(ucenik);
				return new ResponseEntity<Ucenik>(ucenik, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Student doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**************************************** roditelj endpoint*************************************/
	
	@JsonView(Views.Admin.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/parents")
	public ResponseEntity<?> getAllParents() {
		try {
			Iterable<Roditelj> roditelji = roditeljRepository.findAll();
			if(roditelji != null) {
			return new ResponseEntity<Iterable<Roditelj>>(roditelji, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Parents doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	//metoda za pretragu roditelja po jmbg
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/parents/find-by-jmbg")
	public ResponseEntity<?> getRoditeljByJmbg(@RequestParam String jmbg) {
		try {
			Iterable<Roditelj> roditelji = roditeljRepository.findByJmbg(jmbg);
			if (roditelji !=null) {
				return new ResponseEntity<Iterable<Roditelj>>(roditelji, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Parent doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@CrossOrigin(origins="http://localhost:4200")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/parents/{id}")
	public ResponseEntity<?> getRoditeljById(@PathVariable Integer id) {
		try {
			Roditelj roditelj = roditeljRepository.findOne(id);
			if (roditeljRepository.exists(id)) {
				return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Parent doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/parents")
	public ResponseEntity<?> createParent(@RequestBody Roditelj r) {
		try {
			Roditelj roditelj = new Roditelj();
			if((r.getIme() != null && r.getPrezime() != null) && (r.getJmbg() != null && r.getDatumRodjenja() != null)){
				if(r.getIme() != "" && r.getPrezime() != "" && r.getJmbg() != ""){
			roditelj.setIme(r.getIme());
			roditelj.setPrezime(r.getPrezime());
			if(r.getJmbg().length() == 13 ) {
				if(roditeljRepository.findByJmbg(r.getJmbg()).isEmpty()) {
			roditelj.setJmbg(r.getJmbg());
			roditelj.setDatumRodjenja(r.getDatumRodjenja());
	        roditelj.setTipOsobe(TipOsobe.RODITELJ);
			roditeljRepository.save(roditelj);
				return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
				}
				return new ResponseEntity<RestError>(new RestError(4, "Parent didn't saved. Duplicate entry!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(3, "Parent didn't saved. Jmbg must had 13 digites!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(2, "Parent didn't saved. Fill all fields!"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<RestError>(new RestError(1, "Parent didn't saved. Insert all data!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(5, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/parents/{id}")
	public ResponseEntity<?> updateParent(@PathVariable Integer id, @RequestBody Roditelj r) {
		try {
			Roditelj roditelj = roditeljRepository.findOne(id);
			if (roditelj != null) {
				roditelj.setIme(r.getIme());
				roditelj.setPrezime(r.getPrezime());
				roditelj.setJmbg(r.getJmbg());
				roditelj.setDatumRodjenja(r.getDatumRodjenja());
				roditelj.setTipOsobe(TipOsobe.RODITELJ);
				roditeljRepository.save(roditelj);
				return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Parent doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/parents/{id}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer id) {
		try {
			Roditelj roditelj = roditeljRepository.findOne(id);
			if (roditelj != null) {
				roditeljRepository.delete(roditelj);
				return new ResponseEntity<Roditelj>(roditelj, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Parent doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}



	/*************************************** endpoint za admina*************************************/
	
	// metoda za pronalazenje admina po jmbg
	@JsonView(Views.Public.class)
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/admin/find-by-jmbg")
	public ResponseEntity<?> getByJmbg(@RequestParam String jmbg) {
		try {
			Iterable<Admin> admini = adminRepository.findByJmbg(jmbg);
			if(admini != null) {
			return new ResponseEntity<Iterable<Admin>>(admini, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Admin doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllAdmins() {
		try {
			Iterable<Admin> admini = adminRepository.findAll();
			if(admini != null) {
			return new ResponseEntity<Iterable<Admin>>(admini, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Admin doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin/{id}")
	public ResponseEntity<?> getAdminById(@PathVariable Integer id) {
		try {
			Admin admin = adminRepository.findOne(id);
			if (adminRepository.exists(id)) {
				return new ResponseEntity<Admin>(admin, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Admin doesn't found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/admin")
	public ResponseEntity<?> createAdmin(@RequestBody Admin a) {
		try {
			Admin admin = new Admin();
			if((a.getIme() != null && a.getPrezime() != null) && (a.getJmbg() != null && a.getDatumRodjenja() != null)){
				if(a.getIme() != "" && a.getPrezime() != "" && a.getJmbg() != ""){
			admin.setIme(a.getIme());
			admin.setPrezime(a.getPrezime());
			if(a.getJmbg().length() == 13 ) {
				if(adminRepository.findByJmbg(a.getJmbg()).isEmpty()) {
			admin.setJmbg(a.getJmbg());
			admin.setDatumRodjenja(a.getDatumRodjenja());
	        admin.setTipOsobe(TipOsobe.ADMIN);
			adminRepository.save(admin);
				return new ResponseEntity<Admin>(admin, HttpStatus.OK);
			}
				return new ResponseEntity<RestError>(new RestError(4, "Admin didn't saved. Duplicate entry!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(3, "Admin didn't saved. Jmbg must had 13 digites!"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RestError>(new RestError(2, "Admin didn't saved. Fill all fields!"), HttpStatus.NOT_FOUND);	
		}
		return new ResponseEntity<RestError>(new RestError(1, "Admin didn't saved. Insert all data!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(5, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/admin/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody Admin a) {
		try {
			Admin admin = adminRepository.findOne(id);
			if (admin != null) {
				admin.setIme(a.getIme());
				admin.setPrezime(a.getPrezime());
				admin.setJmbg(a.getJmbg());
				adminRepository.save(admin);
				return new ResponseEntity<Admin>(admin, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "User doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/{id}")
	public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
		try {
			Admin admin = adminRepository.findOne(id);
			if (admin != null) {
				adminRepository.delete(admin);
				return new ResponseEntity<Admin>(admin, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Admin doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
