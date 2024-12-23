package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.ComentarioRequest;
import br.com.cwi.instapet.domain.Comentario;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.mapper.ComentarioMapper;
import br.com.cwi.instapet.repository.ComentarioRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ComentarPostServiceTest {
    @InjectMocks
    private ComentarPostService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private ComentarioRepository comentarioRepository;
    @Mock
    private NowService nowService;

    @Captor
    private ArgumentCaptor<Comentario> comentarioArgumentCaptor;


    @Test
    @DisplayName("Deve postar um comentario em um post que existe")
    void devePostarUmComentario(){

        ComentarioRequest request = new ComentarioRequest();
        request.setComentario("Comentario teste");
        LocalDateTime agora = LocalDateTime.now();

        Usuario usuario = UsuarioFactory.get(1L);
        Post post = PostFactory.get();
        List<Comentario> comentariosEsperados = post.getComentarios();
        comentariosEsperados.add(ComentarioMapper.toEntity(request));

        when(nowService.now()).thenReturn(agora);
        when(buscarUsuarioService.porId(usuario.getId())).thenReturn(usuario);
        when(buscarPostService.porId(post.getId())).thenReturn(post);

        tested.comentar(usuario.getId(), post.getId(), request);

        verify(comentarioRepository).save(comentarioArgumentCaptor.capture());
        Comentario comentario = comentarioArgumentCaptor.getValue();

        assertEquals(comentario.getComentario(), request.getComentario());
        assertEquals(post.getComentarios(), comentariosEsperados);
    }

    @Test
    @DisplayName("Nao deve fazer um comentario para um post que nao existe")
    void naoComentarEmPostQueNaoExiste(){
        Long idPost = 55L;
        Usuario usuario = UsuarioFactory.get(1L);
        ComentarioRequest request = new ComentarioRequest();
        request.setComentario("Comentario teste");

        doThrow(ResponseStatusException.class).when(buscarPostService).porId(idPost);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.comentar(usuario.getId(), idPost, request);
        });

        verify(comentarioRepository, never()).save(any());

    }
}
