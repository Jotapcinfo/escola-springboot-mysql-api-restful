package br.com.jotapcinfo.apiescola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jotapcinfo.apiescola.entitie.Participante;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {


}