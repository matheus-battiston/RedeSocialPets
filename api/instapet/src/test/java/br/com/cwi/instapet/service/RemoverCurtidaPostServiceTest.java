package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.validator.ValidatorCurtida;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class RemoverCurtidaPostServiceTest {
    @InjectMocks
    private RemoverCurtidaPostService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ValidatorCurtida validatorCurtida;

    @Captor
    private ArgumentCaptor<Post> argumentCaptorPost;


    @Test
    @DisplayName("Deve dar erro quando usuario nao existir")

    void deveDarErroQuandoUsuarioNaoExistir(){
        Long usuarioId = 1L;
        Long postId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(usuarioId, postId);
        });

        verify(validatorCurtida, never()).validarJaCurtiu(any(), any());
        verify(postRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve dar erro quando post nao existir")

    void deveDarErroQuandoPostNaoExistir(){
        Long usuarioId = 1L;
        Long postId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porId(usuarioId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(usuarioId, postId);
        });

        verify(validatorCurtida, never()).validarJaCurtiu(any(), any());
        verify(postRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve dar erro quando post ja foi curtido")

    void deveDarErroQuandoPostJaFoiCurtido(){
        Long usuarioId = 1L;
        Long postId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Post post = PostFactory.get();
        usuario.getCurtidas().add(post);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarPostService.porId(postId)).thenReturn(post);
        doThrow(ResponseStatusException.class).when(validatorCurtida).validarJaCurtiu(usuario.getCurtidas(), post);

        assertThrows(ResponseStatusException.class, () -> {
            tested.remover(usuarioId, postId);
        });

        verify(validatorCurtida).validarJaCurtiu(usuario.getCurtidas(), post);
        verify(postRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover uma curtida de um post")
    void deveRemoverACurtidaDoPost(){
        Long usuarioId = 1L;
        Long postId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Post post = PostFactory.get();
        usuario.getCurtidas().add(post);
        post.getUsuariosQueCurtiram().add(usuario);
        post.setNumeroLikes(1);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarPostService.porId(postId)).thenReturn(post);

        tested.remover(usuarioId, postId);

        verify(postRepository).save(argumentCaptorPost.capture());
        assertEquals(post.getNumeroLikes(), 0);
        assertEquals(post.getUsuariosQueCurtiram().size(), 0);
        assertEquals(usuario.getCurtidas().size(), 0);


    }
}
