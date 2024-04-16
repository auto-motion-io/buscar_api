package org.motion.buscar_api.application.dtos.gerente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginGerenteRequest(
        @NotNull @NotBlank
        String email,
        @NotNull @NotBlank
        String senha
) { }
