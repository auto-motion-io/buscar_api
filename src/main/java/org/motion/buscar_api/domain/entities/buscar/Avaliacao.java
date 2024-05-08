package org.motion.buscar_api.domain.entities.buscar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.buscar_api.domain.entities.Oficina;

@Table(name = "Buscar_Avaliacao")
@Entity(name = "Avaliacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAvaliacao;
    private Double nota;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "fkUsuario") @NotNull @JsonIgnore
    private Usuario usuarioAvaliacao;
    @ManyToOne
    @JoinColumn(name = "fkOficina") @NotNull @JsonIgnore
    private Oficina oficinaAvaliacao;


}
