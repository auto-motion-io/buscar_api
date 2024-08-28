package org.motion.buscar_api.application.dtos.UsuarioDTO;

public record GoogleResponseDTO(
        Integer idUsuario,
        String nome,
        String chave,
        String fotoUrl) {
}
