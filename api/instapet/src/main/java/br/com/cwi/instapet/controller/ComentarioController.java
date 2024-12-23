package br.com.cwi.instapet.controller;

import br.com.cwi.instapet.controller.request.ComentarioRequest;
import br.com.cwi.instapet.controller.response.ComentarioResponse;
import br.com.cwi.instapet.security.service.UsuarioAutenticadoService;
import br.com.cwi.instapet.service.ComentarPostService;
import br.com.cwi.instapet.service.ListarComentariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    @Autowired
    private ComentarPostService comentarPostService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ListarComentariosService listarComentariosService;

    @PostMapping("/comentar/{idPost}")
    @ResponseStatus(OK)
    public void comentar(@Valid  @RequestBody ComentarioRequest request, @PathVariable Long idPost){
        Long idUsuarioLogado = usuarioAutenticadoService.getId();
        comentarPostService.comentar(idUsuarioLogado, idPost, request);
    }

    @GetMapping("/listar/{postId}")
    @ResponseStatus(OK)
    public List<ComentarioResponse> listarComentarios(@PathVariable long postId){
        return listarComentariosService.listar(postId);
    }
}
