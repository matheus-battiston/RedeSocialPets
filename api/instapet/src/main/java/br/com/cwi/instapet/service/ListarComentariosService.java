package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.ComentarioResponse;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.mapper.ComentarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarComentariosService {
    @Autowired
    private BuscarPostService buscarPostService;
    public List<ComentarioResponse> listar(long postId) {
        Post post = buscarPostService.porId(postId);
        return post.getComentarios().stream()
                .map(ComentarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}
