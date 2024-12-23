package br.com.cwi.instapet.domain;

import br.com.cwi.instapet.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
@Entity
public class PedidoAmizade {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requerente")
    private Usuario requerente;

    @ManyToOne
    @JoinColumn(name = "requisitado")
    private Usuario requisitado;
}
