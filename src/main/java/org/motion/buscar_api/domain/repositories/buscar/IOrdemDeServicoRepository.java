package org.motion.buscar_api.domain.repositories.buscar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdemDeServicoRepository extends JpaRepository<OrdemDeServico,Integer> {
    OrdemDeServico findByToken (String token);
}
