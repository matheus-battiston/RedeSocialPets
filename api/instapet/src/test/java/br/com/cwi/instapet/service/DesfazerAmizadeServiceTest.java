package br.com.cwi.instapet.service;

import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import br.com.cwi.instapet.validator.ValidarUsuariosSaoAmigos;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class DesfazerAmizadeServiceTest {
    @InjectMocks
    private DesfazerAmizadeService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private ValidarUsuariosSaoAmigos validarUsuariosSaoAmigos;

    @Mock
    private UsuarioRepository repository;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptorAmigo;
    @Test
    @DisplayName("Deve desfazer uma amizade")
    void desfazerAmizade(){
        Long usuarioId = 1L;
        Long amigoId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigo = UsuarioFactory.get(amigoId);
        List<Usuario> esperado = new ArrayList<>();

        usuario.getAmigos().add(amigo);
        amigo.getAmigos().add(usuario);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarUsuarioService.porId(amigoId)).thenReturn(amigo);

        tested.desfazer(usuarioId, amigoId);

        verify(validarUsuariosSaoAmigos).validar(usuario, amigo);
        verify(repository).save(usuarioArgumentCaptor.capture());

        assertEquals(esperado, usuarioArgumentCaptor.getValue().getAmigos());
    }

    @Test
    @DisplayName("Nao deve desfazer uma amizade em que nao sejam amigos")
    void naoDesfazerAmizadeSeElaNaoExiste(){
        Long usuarioId = 1L;
        Long amigoId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigo = UsuarioFactory.get(amigoId);

        usuario.getAmigos().add(amigo);
        amigo.getAmigos().add(usuario);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(buscarUsuarioService.porId(amigoId)).thenReturn(amigo);
        doThrow(ResponseStatusException.class).when(validarUsuariosSaoAmigos).validar(usuario, amigo);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.desfazer(usuarioId, amigoId);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Nao deve desfazer amizade se o usuario nao existe")
    void naoDesfazerAmizadeSeUsuarioNaoExiste(){
        Long usuarioId = 1L;
        Long amigoId = 5L;
        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.desfazer(usuarioId, amigoId);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Nao deve desfazer amizade se o amigo nao existe")
    void naoDesfazerAmizadeSeAmigoNaoExiste(){
        Long usuarioId = 1L;
        Long amigoId = 5L;
        Usuario usuario = UsuarioFactory.get(usuarioId);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(amigoId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.desfazer(usuarioId, amigoId);
        });

        verify(repository, never()).save(any());
    }
}
