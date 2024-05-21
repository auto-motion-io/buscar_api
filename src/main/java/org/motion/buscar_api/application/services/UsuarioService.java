package org.motion.buscar_api.application.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.motion.buscar_api.application.dtos.UsuarioDTO.CreateUsuarioDTO;
import org.motion.buscar_api.application.dtos.UsuarioDTO.LoginUsuarioRequest;
import org.motion.buscar_api.application.dtos.UsuarioDTO.LoginUsuarioResponse;
import org.motion.buscar_api.application.dtos.UsuarioDTO.UpdateSenhaUsuarioDTO;
import org.motion.buscar_api.application.exception.DadoUnicoDuplicadoException;
import org.motion.buscar_api.application.exception.RecursoNaoEncontradoException;
import org.motion.buscar_api.domain.entities.buscar.Usuario;
import org.motion.buscar_api.domain.repositories.buscar.IUsuarioRepository;
import org.motion.buscar_api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Usuario não encontrado com o id: " + id));
    }

    public UserDetails buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario criar(CreateUsuarioDTO novoUsuarioDTO) {
        verificarEmailDuplicado(novoUsuarioDTO.email());
        Usuario usuario = new Usuario(novoUsuarioDTO);
        String senhaCriptografada = new BCryptPasswordEncoder().encode(novoUsuarioDTO.senha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public LoginUsuarioResponse login(@Valid LoginUsuarioRequest request){
        Usuario usuario = (Usuario) buscarPorEmail(request.email());
        if (usuario == null) {
            throw new RecursoNaoEncontradoException("Email não encontrado");
        }
        if (!new BCryptPasswordEncoder().matches(request.senha(), usuario.getSenha())) {
            throw new RecursoNaoEncontradoException("Senha incorreta");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return new LoginUsuarioResponse(usuario.getIdUsuario(), token);
    }


    @Transactional
    public Usuario atualizarSenha(int id, UpdateSenhaUsuarioDTO updateSenhaUsuarioDTO) {
        Usuario usuario = buscarPorId(id);
        String senhaCriptografada = new BCryptPasswordEncoder().encode(updateSenhaUsuarioDTO.senha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    private void emailRecuperarSenha(String email) throws MessagingException {
        String htmlBody = "<html>" +
                "<head><link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap' rel='stylesheet'></head>" +
                "<body style='font-family: Roboto, sans-serif;'><h2>Recebemos uma mensagem informando que você esqueceu sua senha.<br> Se foi você, pode redefinir a senha agora.</h2>" +
                "<a href='https://www.google.com' style='padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;'>Ir para o Google</a>"+
                "<p>Atenciosamente,</p>" +
                "<p>A equipe motion</p>" +
                "</body></html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Recuperação de senha");
        helper.setText(htmlBody, true); // Habilita o processamento de HTML
        emailSender.send(message);
    }
   public boolean enviarEmailRecuperacaoSenha(String email) throws MessagingException {
        boolean exists = usuarioRepository.existsByEmail(email);
        if (!exists) {
            throw new RecursoNaoEncontradoException("Email não encontrado");
        }
        emailRecuperarSenha(email);
        return true;
   }

   public void deletar(int id){
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
   }

    private void verificarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new DadoUnicoDuplicadoException("Email já cadastrado");
        }
    }
}
