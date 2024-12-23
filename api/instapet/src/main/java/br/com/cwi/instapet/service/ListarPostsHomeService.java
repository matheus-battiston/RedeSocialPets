package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.PostHomeResponse;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.mapper.ListarPostsMapper;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPostsHomeService {

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private PostRepository postRepository;

    public Page<PostHomeResponse> listar(Long idUsuario, Pageable pageable) {
        Usuario usuario = buscarUsuarioService.porId(idUsuario);
        Page<Post> posts = postRepository.findTodosPostsDeAmigos(usuario.getAmigos(), usuario, pageable);

        return posts.map(post -> ListarPostsMapper.toResponse(post, usuario.getCurtidas()));
    }
}
