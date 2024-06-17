package org.motion.buscar_api.application.dtos.UsuarioDTO;

public record LoginUsuarioResponse(
        Integer idUsuario,
        String nome,
        String token,
        String fotoUrl
) {
}
