package org.motion.buscar_api.application.dtos.gerente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSenhaGerenteDTO(
        @NotNull @NotBlank
        String senha
) {
}

