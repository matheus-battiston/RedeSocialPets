package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.ComentarioRequest;
import br.com.cwi.instapet.domain.Comentario;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.mapper.ComentarioMapper;
import br.com.cwi.instapet.repository.ComentarioRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
public class ComentarPostService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private NowService nowService;

    @Transactional
    public void comentar(Long idUsuarioLogado, Long idPost, @Valid ComentarioRequest request) {
        Usuario usuario = buscarUsuarioService.porId(idUsuarioLogado);
        Post post = buscarPostService.porId(idPost);

        Comentario comentario = ComentarioMapper.toEntity(request);
        comentario.setPost(post);
        comentario.setUsuario(usuario);
        comentario.setHorario(nowService.now());

        post.addComentario(comentario);

        comentarioRepository.save(comentario);
    }
}
