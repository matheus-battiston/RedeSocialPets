package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.NovoPostRequest;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.instapet.mapper.PostMapper.toEntity;

@Service
public class PostarService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private NowService nowService;

    @Transactional
    public void postar(Long id, NovoPostRequest request) {
        Usuario usuario = buscarUsuarioService.porId(id);
        Post post = toEntity(request);

        post.setUsuario(usuario);
        post.setHorario(nowService.now());
        post.setNumeroLikes(0);

        usuario.adicionarPost(post);

        postRepository.save(post);
    }
}
