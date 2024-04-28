package org.motion.buscar_api.application.dtos.UsuarioDTO;

public record LoginUsuarioResponse(
        Integer idUsuario,
        String token
) {
}
