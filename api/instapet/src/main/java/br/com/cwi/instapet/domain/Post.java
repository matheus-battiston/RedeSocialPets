package br.com.cwi.instapet.domain;

import br.com.cwi.instapet.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String urlImagem;
    private String legenda;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Integer numeroLikes;
    @Enumerated(STRING)
    private PermissaoPost permissao;

    private LocalDateTime horario;

    @OneToMany(mappedBy = "post")
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(mappedBy = "curtidas")
    private List<Usuario> usuariosQueCurtiram = new ArrayList<>();


    public void adicionarCurtida(Usuario usuario){
        this.getUsuariosQueCurtiram().add(usuario);
        this.numeroLikes++;
        usuario.addPostCurtido(this);
    }

    public void removerCurtida(Usuario usuario) {
        this.getUsuariosQueCurtiram().remove(usuario);
        this.numeroLikes--;
        usuario.removerPostCurtido(this);
    }

    public void addComentario(Comentario comentario) {
        this.getComentarios().add(comentario);
        comentario.setPost(this);
    }
}
