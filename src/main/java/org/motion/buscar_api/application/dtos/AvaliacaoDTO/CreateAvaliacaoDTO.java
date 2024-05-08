package org.motion.buscar_api.application.dtos.AvaliacaoDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;


public record CreateAvaliacaoDTO(
        @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "5.0")
        Double nota,
        @NotBlank
        String comentario,
        @NotNull @JsonIgnore
        Integer fkUsuario,
        @NotNull @JsonIgnore
        Integer fkOficina
) {
}
