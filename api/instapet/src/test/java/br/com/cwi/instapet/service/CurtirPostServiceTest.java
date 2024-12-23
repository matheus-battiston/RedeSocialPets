package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.repository.PostRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.validator.ValidatorCurtida;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class CurtirPostServiceTest {
    @InjectMocks
    private CurtirPostService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;
    @Mock
    private BuscarPostService buscarPostService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ValidatorCurtida validatorCurtida;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;


    @Test
    @DisplayName("Deve curtir um post")
    void deveCurtirUmPost(){
        Long idUsuario = 1L;
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get(idUsuario);
        List<Usuario> pessoasQueCurtiramEsperadas = post.getUsuariosQueCurtiram();
        pessoasQueCurtiramEsperadas.add(usuario);

        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);
        when(buscarPostService.porId(post.getId())).thenReturn(post);

        tested.curtir(idUsuario, post.getId());

        verify(validatorCurtida).validarNaoCurtiu(usuario.getCurtidas(), post);
        verify(postRepository).save(postArgumentCaptor.capture());

        assertEquals(postArgumentCaptor.getValue().getUsuariosQueCurtiram(), pessoasQueCurtiramEsperadas);
    }

    @Test
    @DisplayName("Nao deve curtir um post que ja foi curtido")
    void naoCurtirOqueJaCurtiu(){
        Long idUsuario = 1L;
        Post post = PostFactory.get();
        Usuario usuario = UsuarioFactory.get(idUsuario);
        usuario.getCurtidas().add(post);
        post.getUsuariosQueCurtiram().add(usuario);

        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);
        when(buscarPostService.porId(post.getId())).thenReturn(post);
        doThrow(ResponseStatusException.class).when(validatorCurtida).validarNaoCurtiu(usuario.getCurtidas(), post);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(idUsuario, post.getId());
        });
        verify(postRepository, never()).save(any());
    }

    @Test
    @DisplayName("Nao deve curtir um post que nao existe")
    void naoCurtirPostInexistende(){
        Long idUsuario = 1L;
        Long idPost = 99L;
        Usuario usuario = UsuarioFactory.get(idUsuario);

        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarPostService).porId(idPost);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(idUsuario, idPost);
        });

        verify(postRepository, never()).save(any());

    }

    @Test
    @DisplayName("Nao deve curtir um post com um usuario inexistente")
    void naoDeveCurtirSemUmUsuario(){
        Long idUsuario = 1L;
        Long idPost = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(idUsuario);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.curtir(idUsuario, idPost);
        });

        verify(postRepository, never()).save(any());

    }
}
