package org.motion.buscar_api.application.controllers.buscar;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.motion.buscar_api.application.dtos.UsuarioDTO.CreateUsuarioDTO;
import org.motion.buscar_api.application.dtos.UsuarioDTO.LoginUsuarioRequest;
import org.motion.buscar_api.application.dtos.UsuarioDTO.LoginUsuarioResponse;
import org.motion.buscar_api.application.dtos.UsuarioDTO.UpdateSenhaUsuarioDTO;
import org.motion.buscar_api.application.services.UsuarioService;
import org.motion.buscar_api.domain.entities.buscar.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "motion_jwt")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.status(200).body(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody CreateUsuarioDTO usuario) {
        Usuario usuarioCadastrado = usuarioService.criar(usuario);
        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @PostMapping("/login") @PermitAll
    public ResponseEntity<LoginUsuarioResponse> login(@RequestBody @Valid LoginUsuarioRequest usuario) {
        LoginUsuarioResponse usuarioLogado = usuarioService.login(usuario);
        return ResponseEntity.status(200).body(usuarioLogado);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestParam String email) throws MessagingException {
        usuarioService.enviarEmailRecuperacaoSenha(email);
        return ResponseEntity.status(200).body("Email enviado com sucesso");
    }

    @PutMapping("/atualizar-senha")
    public ResponseEntity<Usuario> atualizarSenha(@Valid @RequestParam int id, @RequestBody UpdateSenhaUsuarioDTO updateSenhaUsuarioDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizarSenha(id,updateSenhaUsuarioDTO);
        return ResponseEntity.status(200).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(200).body("Usuario deletado com sucesso");
    }

}
