package com.iktpreobuka.ednevnik.controllers;

import java.util.Date;
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
import com.iktpreobuka.ednevnik.dto.OcenaDTO;
import com.iktpreobuka.ednevnik.entities.Nastavnik;
import com.iktpreobuka.ednevnik.entities.Ocena;

import com.iktpreobuka.ednevnik.entities.Predmet;
import com.iktpreobuka.ednevnik.enums.TipOcene;
import com.iktpreobuka.ednevnik.entities.Ucenik;
import com.iktpreobuka.ednevnik.repositories.NastavnikRepository;
import com.iktpreobuka.ednevnik.repositories.OcenaRepository;
import com.iktpreobuka.ednevnik.repositories.PredmetRepository;
import com.iktpreobuka.ednevnik.repositories.UcenikRepository;
import com.iktpreobuka.ednevnik.security.Views;
import com.iktpreobuka.ednevnik.services.OcenaDao;

@RestController
@RequestMapping("/api/v1/grades")
public class OcenaController {

	@Autowired
	private OcenaRepository ocenaRepository;

	@Autowired
	private UcenikRepository ucenikRepository;

	@Autowired
	private PredmetRepository predmetRepository;

	@Autowired
	private NastavnikRepository nastavnikRepository;

	@Autowired
	private OcenaDao ocenaDao;


	@CrossOrigin(origins = "http://localhost:4200")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllGradesAdmin() {
		try {
			Iterable<Ocena> ocene = ocenaRepository.findAll();
			return new ResponseEntity<Iterable<Ocena>>(ocene, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@JsonView(Views.Admin.class)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getGradeById(@PathVariable Integer id) {
		try {
			Ocena ocena = ocenaRepository.findOne(id);
			if (ocenaRepository.exists(id)) {
				return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Grade doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	// metoda koja vraca ocene na osnovu predmeta i ucenika
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/subject/{pId}/student/{uId}")
	public ResponseEntity<?> getGradesByTeacherSubjectAndStudent(@PathVariable Integer pId, @PathVariable Integer uId) {
	     
		try {
			List<Ocena> ocene = ocenaDao.findOceneByPredmetAndUcenik(pId, uId);
			if (!ocene.isEmpty()) {
				return new ResponseEntity<List<Ocena>>(ocene, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Grades doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// metoda za dodavanje ocena,moguce je uneti samo deset ocena po premetu za
	// jednog ucenika
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/teacher/{Nid}/subject/{Pid}/student/{Uid}")
	public ResponseEntity<?> createGrade(@PathVariable Integer Nid, @PathVariable Integer Pid,
			@PathVariable Integer Uid, @RequestBody OcenaDTO o) {
		try {
			Ocena ocena = new Ocena();
			Nastavnik nastavnik = nastavnikRepository.findOne(Nid);
			if (nastavnik != null) {
				Predmet predmet = predmetRepository.findOne(Pid);
				if (predmet != null) {
					if (nastavnikRepository.getNastavnikByPredmet(Pid) == Nid) {
						Ucenik ucenik = ucenikRepository.findOne(Uid);
						if (ucenik != null) {
							if (ocenaRepository.countByVrednost(predmet.getId(), ucenik.getId()) < 10) {
									if(o.getVrednost() >= 1 && o.getVrednost() <= 5) {
								if (o.getTipOcene() != TipOcene.ZAKLJUCNA_OCENA_PG && o.getTipOcene() != TipOcene.ZAKLJUCNA_OCENA_KG) {
									ocena.setId(o.getId());
									ocena.setPolugodiste(o.getPolugodiste());
									ocena.setDatumUnosa(new Date());
									ocena.setTipOcene(o.getTipOcene());
									ocena.setVrednost(o.getVrednost());
									ocena.setDeleted(false);
									ocena.setNastavnik(nastavnik);
									ocena.setPredmet(predmet);
									ocena.setUcenik(ucenik);
									ocenaRepository.save(ocena);
									return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
								} else {
									return new ResponseEntity<RestError>(
											new RestError(7, "Grade didn't saved! Grade type can be only between 0-3!"),
											HttpStatus.NOT_FOUND);
								}
									} else {
										return new ResponseEntity<RestError>(new RestError(6, "Grade didn't saved!Only values between 1-5 can be inserted!"),
												HttpStatus.NOT_FOUND);
									}
							} else {
								return new ResponseEntity<RestError>(new RestError(5,"Grade didn't saved! Only 10 grades per subject can be inserted!"),
										HttpStatus.NOT_FOUND);
							}
						} else {
							return new ResponseEntity<RestError>(
									new RestError(4, "Grade didn't saved! Student doesn't exists!"),
									HttpStatus.NOT_FOUND);

						}
					} else {
						return new ResponseEntity<RestError>(new RestError(3, "Grade didn't saved!Inadmissible entry!"),
								HttpStatus.NOT_FOUND);
					}
				} else {
					return new ResponseEntity<RestError>(new RestError(2, "Grade didn't saved!Subject doesn't exists!"),
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<RestError>(new RestError(1, "Grade didn't saved! Teacher doesn't exists!"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(8, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//// zakljucivanje ocene na polugodistu
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/avg-grade-first-sem/teacher/{Nid}/subject/{Pid}/student/{Uid}")
	public ResponseEntity<?> avgGradeByFirstSem(@PathVariable Integer Nid, @PathVariable Integer Pid,
			@PathVariable Integer Uid, @RequestBody OcenaDTO o) {
		try {
			Ocena ocena = new Ocena();
			Nastavnik nastavnik = nastavnikRepository.findOne(Nid);
			if (nastavnik != null) {
				Predmet predmet = predmetRepository.findOne(Pid);
				if (predmet != null) {
					if (nastavnikRepository.getNastavnikByPredmet(Pid) == Nid) {
						Ucenik ucenik = ucenikRepository.findOne(Uid);
						if (ucenik != null) {
							if (ocenaRepository.countByVrednost(predmet.getId(), ucenik.getId()) < 10) {
									if(o.getVrednost() >= 1 && o.getVrednost() <= 5) {
								ocena.setId(o.getId());
								ocena.setPolugodiste(1);
								ocena.setDatumUnosa(new Date());
								ocena.setTipOcene(TipOcene.ZAKLJUCNA_OCENA_PG);
								ocena.setVrednost(o.getVrednost());
								ocena.setDeleted(false);
								ocena.setNastavnik(nastavnik);
								ocena.setPredmet(predmet);
								ocena.setUcenik(ucenik);
								ocenaRepository.save(ocena);
								return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
									} else {
										return new ResponseEntity<RestError>(new RestError(6,
												"Grade didn't saved!Only values between 1-5 can be inserted!"),
												HttpStatus.NOT_FOUND);
									}
							} else {
								return new ResponseEntity<RestError>(new RestError(5,
										"Grade didn't saved! Only 10 grades per subject can be inserted!"),
										HttpStatus.NOT_FOUND);
							}
						} else {
							return new ResponseEntity<RestError>(
									new RestError(4, "Grade didn't saved! Student doesn't exists!"),
									HttpStatus.NOT_FOUND);

						}
					} else {
						return new ResponseEntity<RestError>(new RestError(3, "Grade didn't saved!Inadmissible entry!"),
								HttpStatus.NOT_FOUND);
					}
				} else {
					return new ResponseEntity<RestError>(new RestError(2, "Grade didn't saved!Subject doesn't exists!"),
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<RestError>(new RestError(1, "Grade didn't saved! Teacher doesn't exists!"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(7, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	//// zakljucivanje ocene na kraju godine
    @CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST, value = "/final-grade/teacher/{Nid}/subject/{Pid}/student/{Uid}")
	public ResponseEntity<?> finalGrade(@PathVariable Integer Nid, @PathVariable Integer Pid, @PathVariable Integer Uid,
			@RequestBody OcenaDTO o) {
		try {
			Ocena ocena = new Ocena();
			Nastavnik nastavnik = nastavnikRepository.findOne(Nid);
			if (nastavnik != null) {
				Predmet predmet = predmetRepository.findOne(Pid);
				if (predmet != null) {
					if (nastavnikRepository.getNastavnikByPredmet(Pid) == Nid) {
						Ucenik ucenik = ucenikRepository.findOne(Uid);
						if (ucenik != null) {
							if (ocenaRepository.countByVrednost(predmet.getId(), ucenik.getId()) < 10) {
								if(ocenaRepository.findByTipOceneAndUcenikAndPredmet(TipOcene.ZAKLJUCNA_OCENA_PG,ucenik, predmet) != null) {
									 if(o.getVrednost() >= 1 && o.getVrednost() <= 5) {
								ocena.setId(o.getId());
								ocena.setPolugodiste(2);
								ocena.setDatumUnosa(new Date());
								ocena.setTipOcene(TipOcene.ZAKLJUCNA_OCENA_KG);
								ocena.setVrednost(o.getVrednost());
								ocena.setDeleted(false);
								ocena.setNastavnik(nastavnik);
								ocena.setPredmet(predmet);
								ocena.setUcenik(ucenik);
								ocenaRepository.save(ocena);
								return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
										} else {
											return new ResponseEntity<RestError>(new RestError(7,"Grade didn't saved!Only values between 1-5 can be inserted!"),
													HttpStatus.NOT_FOUND);
										}
								} else {
											return new ResponseEntity<RestError>(new RestError(6, "Grade didn't saved! Final grade for first semester must be inserted!"),
													HttpStatus.NOT_FOUND);
									}
							} else {
								return new ResponseEntity<RestError>(new RestError(5, "Grade didn't saved!Only 10 grades per subject can be inserted."),
										HttpStatus.NOT_FOUND);
							}
						} else {
							return new ResponseEntity<RestError>(
									new RestError(4, "Grade didn't saved! Student doesn't exists!"),
									HttpStatus.NOT_FOUND);

						}
					} else {
						return new ResponseEntity<RestError>(new RestError(3, "Grade didn't saved! Inadmissible entry!"),
								HttpStatus.NOT_FOUND);
					}
				} else {
					return new ResponseEntity<RestError>(new RestError(2, "Grade didn't saved!Subject doesn't exists!"),
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<RestError>(new RestError(1, "Grade didn't saved! Teacher doesn't exists!"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(8, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateGrade(@PathVariable Integer id, @RequestBody Ocena o) {
		try {
			Ocena ocena = ocenaRepository.findOne(id);
			if (ocena != null) {
				ocena.setDatumUnosa(new Date());
				ocena.setPolugodiste(o.getPolugodiste());
				ocena.setTipOcene(o.getTipOcene());
				ocena.setVrednost(o.getVrednost());
				ocena.setDeleted(false);
				ocenaRepository.save(ocena);
				return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
			}
			return new ResponseEntity<RestError>(new RestError(1, "Grade doesn't exist."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestError>(new RestError(2, "Exception occured: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	 @CrossOrigin(origins = "http://localhost:4200")
	 @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	 public ResponseEntity<?> deleteGradeLogical(@PathVariable Integer id) {
	 try {
	 Ocena ocena = ocenaRepository.findOne(id);
	 if (ocena != null) {
	 ocena.setDeleted(true);
	 ocenaRepository.save(ocena);
	 return new ResponseEntity<Ocena>(ocena, HttpStatus.OK);
	 }
	 return new ResponseEntity<RestError>(new RestError(1, "Grade doesn't exist."),HttpStatus.NOT_FOUND);
	 } catch (Exception e) {
	 return new ResponseEntity<RestError>(new RestError(1, "Exception occured: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	
	 }

}
