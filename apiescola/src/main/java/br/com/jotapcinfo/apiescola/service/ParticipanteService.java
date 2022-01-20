package br.com.jotapcinfo.apiescola.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jotapcinfo.apiescola.entitie.Participante;
import br.com.jotapcinfo.apiescola.entitie.exception.ExceptionClass;
import br.com.jotapcinfo.apiescola.repository.ParticipanteRepository;

@Service
public class ParticipanteService {
	
	@Autowired
	private ParticipanteRepository repository;
	
	public Participante saveMoreAge(Participante participante) {
		
		LocalDate start = participante.getNascimento();
		LocalDate end = LocalDate.now();
		long years = ChronoUnit.YEARS.between(start, end);
		
		if (years >= 18)
			return repository.save(participante);
		
		throw new ExceptionClass("O participante deve ter mais que 18 anos de idade.");
	}
}
