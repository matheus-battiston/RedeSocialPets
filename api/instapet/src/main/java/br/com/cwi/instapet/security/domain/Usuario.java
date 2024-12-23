package br.com.cwi.instapet.security.domain;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.domain.Post;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    private String urlFotoPerfil;
    private LocalDate dataNascimento;

    private String apelido;

    @OneToMany(mappedBy = "usuario")
    private List<Post> posts = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "amizade",
            joinColumns = @JoinColumn(name = "usuario"),
            inverseJoinColumns = @JoinColumn(name = "amigo"))
    private List<Usuario> amigos = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "requisitado")
    private List<PedidoAmizade> pedidosAmizadeRecebidos = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "requerente")
    private List<PedidoAmizade> pedidosAmizadeEnviados = new ArrayList<>();


    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "curtida",
                joinColumns = @JoinColumn(name = "usuario_id"),
                inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> curtidas = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }

    public void addPostCurtido(Post post) {
        this.getCurtidas().add(post);
    }

    public void removerPostCurtido(Post post) {
        this.getCurtidas().remove(post);
    }

    public void addAmigo(Usuario usuarioRequisitado) {
        this.getAmigos().add(usuarioRequisitado);
        usuarioRequisitado.getAmigos().add(this);
    }

    public void addPedidoAmizade(PedidoAmizade pedidoAmizade) {
        this.getPedidosAmizadeEnviados().add(pedidoAmizade);
        pedidoAmizade.getRequisitado().getPedidosAmizadeRecebidos().add(pedidoAmizade);
    }

    public void removerAmigo(Usuario amigo) {
        this.getAmigos().remove(amigo);
        amigo.getAmigos().remove(this);
    }

    public void adicionarPost(Post post) {
        this.getPosts().add(post);
    }
}
