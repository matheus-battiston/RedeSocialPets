package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.ComentarioResponse;
import br.com.cwi.instapet.domain.Comentario;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.ComentarioFactory;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.mapper.ComentarioMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarComentariosServiceTest {
    @InjectMocks
    private ListarComentariosService tested;

    @Mock
    private BuscarPostService buscarPostService;

    @Test
    @DisplayName("Listar comentarios de um post")
    void deveListarComentariosDeUmPost(){
        Long postId = 1L;
        Long idComentario = 1L;
        Long idComentarioDois = 2L;
        Post post = PostFactory.get();
        Comentario comentario = ComentarioFactory.get(idComentario);
        Comentario comentarioDois = ComentarioFactory.get(idComentarioDois);
        post.getComentarios().add(comentario);
        post.getComentarios().add(comentarioDois);

        List<ComentarioResponse> responseEsperada =
                post.getComentarios().stream().
                        map(ComentarioMapper::toResponse)
                        .collect(Collectors.toList());


        when(buscarPostService.porId(postId)).thenReturn(post);

        List<ComentarioResponse> response = tested.listar(postId);

        assertEquals(responseEsperada.get(0).getComentario(), response.get(0).getComentario());
        assertEquals(responseEsperada.get(1).getComentario(), response.get(1).getComentario());
        assertEquals(2, response.size());

    }

    @Test
    @DisplayName("Deve retornar erro se post nao existir")
    void retornarErroSePostNaoExiste(){
        Long postId = 1L;

        doThrow(ResponseStatusException.class).when(buscarPostService).porId(postId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.listar(postId);
        });
    }
}
