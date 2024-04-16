package org.motion.buscar_api.application.dtos.tarefa;

import jakarta.validation.constraints.NotBlank;

public record UpdateTarefaDTO(
        @NotBlank
        String status
) {

}
