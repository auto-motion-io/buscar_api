package org.motion.buscar_api.application.dtos.gerente;

import org.motion.motion_api.domain.entities.Oficina;

public record LoginGerenteResponse
        (
                Integer idGerente,
                String email,
                String sobrenome,
                String status,
                Oficina oficina,
                String token
         )
{
}
