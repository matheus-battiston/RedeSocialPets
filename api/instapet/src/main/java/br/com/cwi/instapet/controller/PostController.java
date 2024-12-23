package br.com.cwi.instapet.controller;

import br.com.cwi.instapet.controller.request.NovoPostRequest;
import br.com.cwi.instapet.controller.response.PostHomeResponse;
import br.com.cwi.instapet.security.service.UsuarioAutenticadoService;
import br.com.cwi.instapet.service.CurtirPostService;
import br.com.cwi.instapet.service.ListarPostsHomeService;
import br.com.cwi.instapet.service.PostarService;
import br.com.cwi.instapet.service.RemoverCurtidaPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private PostarService postarService;
    @Autowired
    private CurtirPostService curtirPostService;
    @Autowired
    private RemoverCurtidaPostService removerCurtidaPostService;

    @Autowired
    private ListarPostsHomeService listarPostsHomeService;

    @PostMapping("/postar")
    public void postar(@Valid @RequestBody NovoPostRequest request){
        Long id = usuarioAutenticadoService.getId();
        postarService.postar(id, request);
    }

    @PostMapping("/curtir/{idPost}")
    public void curtir(@PathVariable Long idPost){
        Long idUsuario = usuarioAutenticadoService.getId();
        curtirPostService.curtir(idUsuario, idPost);
    }

    @PostMapping("/remover-curtida/{idPost}")
    public void removerCurtida(@PathVariable Long idPost){
        Long idUsuario = usuarioAutenticadoService.getId();
        removerCurtidaPostService.remover(idUsuario, idPost);

    }

    @GetMapping("/listar-posts-home")
    public Page<PostHomeResponse> listarPostsHome(Pageable pageable){
        Long idUsuario = usuarioAutenticadoService.getId();
        return listarPostsHomeService.listar(idUsuario, pageable);

    }
}
