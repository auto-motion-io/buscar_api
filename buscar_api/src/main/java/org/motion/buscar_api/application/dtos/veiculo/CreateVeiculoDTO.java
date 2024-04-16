package org.motion.buscar_api.application.dtos.veiculo;

import jakarta.validation.constraints.NotNull;

public record CreateVeiculoDTO(
        @NotNull
        String placa,
        @NotNull
        String marca,
        @NotNull
        String modelo,
        @NotNull
        String cor,
        @NotNull
        Integer ano,
        @NotNull
        Integer fkCliente) {
}
