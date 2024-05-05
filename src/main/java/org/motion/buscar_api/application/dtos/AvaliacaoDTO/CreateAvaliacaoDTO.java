package org.motion.buscar_api.application.dtos.AvaliacaoDTO;

import jakarta.validation.constraints.*;
import org.motion.buscar_api.domain.entities.Oficina;
import org.motion.buscar_api.domain.entities.buscar.Usuario;

public record CreateAvaliacaoDTO(
        @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "5.0")
        Double nota,
        @NotBlank
        String comentario,
        @NotNull
        Usuario fkUsuario,
        @NotNull
        Oficina fkOficina
) {
}
