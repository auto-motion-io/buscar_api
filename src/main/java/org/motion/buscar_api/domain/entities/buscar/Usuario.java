package org.motion.buscar_api.domain.entities.buscar;

import jakarta.persistence.*;
import lombok.*;
import org.motion.buscar_api.application.dtos.UsuarioDTO.CreateUsuarioDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "Buscar_Usuario")
@Entity(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

    public Usuario(CreateUsuarioDTO createUsuarioDTO) {
        this.nome = createUsuarioDTO.nome();
        this.sobrenome = createUsuarioDTO.sobrenome();
        this.email = createUsuarioDTO.email();
        this.senha = createUsuarioDTO.senha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
