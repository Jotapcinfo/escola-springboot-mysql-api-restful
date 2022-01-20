package br.com.jotapcinfo.apiescola.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jotapcinfo.apiescola.entitie.Participante;
import br.com.jotapcinfo.apiescola.repository.ParticipanteRepository;
import br.com.jotapcinfo.apiescola.service.ParticipanteService;

@RestController
@RequestMapping("/participante")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class ParticipanteController {

	@Autowired
	private ParticipanteRepository repository;
	
	@Autowired
	private ParticipanteService service;
	
	@GetMapping
	public ResponseEntity<List<Participante>>getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Participante>getById(
		  @PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp)) //Lambda expression function
				.orElse(ResponseEntity.notFound().build());
		
		//Optional<Participante> participante = repository.findById(participanteId); //usando o Optional
		//if (participante.isPresent()) {
			//return ResponseEntity.ok(participante.get());
		//}
	}
	
	@PostMapping
	public ResponseEntity<Participante>post(
		  @Valid 
		  @RequestBody Participante participante){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(service.saveMoreAge(participante));
	}
	
	@PutMapping("/{participanteId}")
	public ResponseEntity<Participante>put(
			
		  @PathVariable long participanteId, 
		  @Valid
		  @RequestBody Participante participante){
		
		  if (!repository.existsById(participanteId)) {
			return ResponseEntity.notFound().build();

		}
		  participante.setId(participanteId);
		  participante = repository.save(participante);
		  return ResponseEntity.ok(participante);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id){
		repository.deleteById(id);
	}	
}
