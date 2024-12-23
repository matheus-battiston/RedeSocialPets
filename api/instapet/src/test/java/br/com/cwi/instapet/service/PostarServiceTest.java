package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.NovoPostRequest;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
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

import static java.time.LocalDateTime.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PostarServiceTest {
    @InjectMocks
    private PostarService tested;

    @Mock
    private PostRepository postRepository;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;
    @Mock
    private NowService nowService;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Test
    @DisplayName("Deve dar erro quando usuario nao existir")
    void deveDarErroQuandoUsuarioNaoExistir(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.postar(usuarioId, new NovoPostRequest());
        });

        verify(postRepository, never()).save(any());
    }


    @Test
    @DisplayName("Deve fazer um post")
    void deveFazerUmPost(){
        LocalDateTime agora = now();
        Long usuarioId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        NovoPostRequest novoPost = NovoPostRequest.builder()
                .legenda("Legenda post request")
                .urlFoto("URL_FOTO")
                .build();

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(nowService.now()).thenReturn(agora);

        tested.postar(usuarioId, novoPost);

        verify(postRepository).save(postArgumentCaptor.capture());

        assertEquals(postArgumentCaptor.getValue().getUsuario(), usuario);
        assertEquals(postArgumentCaptor.getValue().getLegenda(), novoPost.getLegenda());
        assertEquals(postArgumentCaptor.getValue().getUrlImagem(), novoPost.getUrlFoto());
        assertEquals(postArgumentCaptor.getValue().getHorario(), agora);
        assertTrue(usuario.getPosts().contains(postArgumentCaptor.getValue()));
    }

}
