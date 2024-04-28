package org.motion.buscar_api.application.dtos.UsuarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSenhaUsuarioDTO(
        @NotNull @NotBlank
        String senha) {
}
