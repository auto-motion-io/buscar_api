package org.motion.buscar_api.domain.repositories.buscar;

import org.motion.buscar_api.domain.entities.buscar.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAvaliacaoRepository extends JpaRepository<Avaliacao,Integer> {
}
