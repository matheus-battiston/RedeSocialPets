package br.com.cwi.instapet.controller;

import br.com.cwi.instapet.controller.request.EditarUsuarioRequest;
import br.com.cwi.instapet.controller.response.DetalhesPostResponse;
import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.controller.response.PedidoAmizadeResponse;
import br.com.cwi.instapet.security.service.UsuarioAutenticadoService;
import br.com.cwi.instapet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private DetalharUsuarioService detalharUsuarioService;

    @Autowired
    private EditarUsuarioService editarUsuarioService;

    @Autowired
    private AdicionarAmigoService adicionarAmigoService;

    @Autowired
    private PesquisarUsuarioService pesquisarUsuarioService;

    @Autowired
    private DesfazerAmizadeService desfazerAmizadeService;

    @Autowired
    private ListarAmigosService listarAmigosService;

    @Autowired
    private ListarPedidosAmizadeService listarPedidosAmizadeService;

    @Autowired
    private ListarPostsDeUmUsuarioService listarPostsDeUmUsuarioService;

    @GetMapping("/me")
    public DetalhesUsuarioResponse detalhes(){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        return detalharUsuarioService.detalhar(idUsuarioLogado);
    }

    @PutMapping("/me/editar")
    public DetalhesUsuarioResponse editar(@Valid @RequestBody EditarUsuarioRequest request){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        return editarUsuarioService.editar(idUsuarioLogado, request);
    }

    @PostMapping("/nova-amizade/{idUsuario}")
    @ResponseStatus(OK)
    public void adicionarAmigo(@PathVariable Long idUsuario){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        adicionarAmigoService.adicionar(idUsuarioLogado, idUsuario);
    }

    @PutMapping("/aceitar-amizade/{idPedido}")
    @ResponseStatus(OK)
    public void aceitarAmigo (@PathVariable Long idPedido){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        adicionarAmigoService.aceitar(idUsuarioLogado, idPedido);
    }

    @PutMapping("/recusar-amizade/{idPedido}")
    @ResponseStatus(OK)
    public void recusarAmigo (@PathVariable Long idPedido){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        adicionarAmigoService.recusar(idUsuarioLogado, idPedido);
    }


    @PutMapping("/desfazer-amizade/{idAmigo}")
    @ResponseStatus(OK)
    public void desfazerAmizade (@PathVariable Long idAmigo){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        desfazerAmizadeService.desfazer(idUsuarioLogado, idAmigo);

    }

    @GetMapping("/buscar-usuarios")
    @ResponseStatus(OK)
    public List<DetalhesUsuarioResponse> pesquisar(@RequestParam String texto) {
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        return pesquisarUsuarioService.pesquisar(idUsuarioLogado, texto);
    }

    @GetMapping("/listar-amigos")
    @ResponseStatus(OK)
    public List<DetalhesUsuarioResponse> listarAmigos(@RequestParam(required = false) String texto){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        return listarAmigosService.listar(idUsuarioLogado, texto);
    }

    @GetMapping("/pedidos-amizade-pendente")
    @ResponseStatus(OK)
    public List<PedidoAmizadeResponse> listarPedidosAmizade(){
        long idUsuarioLogado = usuarioAutenticadoService.getId();
        return listarPedidosAmizadeService.listar(idUsuarioLogado);
    }

    @GetMapping("/{idUsuario}/posts")
    @ResponseStatus(OK)
    public List<DetalhesPostResponse> postsDoUsuario(@PathVariable Long idUsuario){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        return listarPostsDeUmUsuarioService.listar(idUsuarioLogado, idUsuario);
    }

}