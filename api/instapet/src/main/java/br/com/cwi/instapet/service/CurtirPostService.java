package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.validator.ValidatorCurtida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtirPostService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ValidatorCurtida validatorCurtida;

    public void curtir(Long idUsuario, Long idPost) {
        Usuario usuario = buscarUsuarioService.porId(idUsuario);
        Post post = buscarPostService.porId(idPost);
        validatorCurtida.validarNaoCurtiu(usuario.getCurtidas(), post);

        post.adicionarCurtida(usuario);

        postRepository.save(post);
    }
}
