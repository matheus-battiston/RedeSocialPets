package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PesquisarUsuarioServiceTest {
    @InjectMocks
    private PesquisarUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve retornar erro caso usuario nao exista")
    void deveRetornarErroCasoUsuarioNaoExista(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.pesquisar(usuarioId, null);
        });

        verify(usuarioRepository, never()).procurarUsuarioPorNomeOuEmail(any(), any(), any());
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuarios filtrados por texto quando lista de amigos nao esta vazia")
    void retornarListaFiltrada(){
        Long usuarioId = 1L;
        Long usuario2Id = 2L;
        Long usuario3Id = 3L;
        String texto = "Nome";

        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario usuario2 = UsuarioFactory.get(usuario2Id);
        Usuario usuario3 = UsuarioFactory.get(usuario3Id);
        usuario.getAmigos().add(usuario3);
        List<Usuario> responseQuery = new ArrayList<>();
        responseQuery.add(usuario2);

        List<DetalhesUsuarioResponse> responseEsperada = responseQuery.stream()
                .map(DetalharUsuarioMapper::toResponse)
                .collect(Collectors.toList());

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(usuarioRepository.procurarUsuarioPorNomeOuEmail(usuario, usuario.getAmigos(), texto))
                .thenReturn(responseQuery);

        List<DetalhesUsuarioResponse> response = tested.pesquisar(usuarioId, texto);

        verify(usuarioRepository).procurarUsuarioPorNomeOuEmail(usuario, usuario.getAmigos(), texto);
        assertEquals(response.get(0).getId(), responseEsperada.get(0).getId());
        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("Deve retornar uma lista de usuarios filtrados por texto quando lista de amigos esta vazia")
    void retornarListaFiltradaListaVazia(){
        Long usuarioId = 1L;
        Long usuario2Id = 2L;
        Long usuario3Id = 3L;
        String texto = "Nome";

        List<Usuario> all = new ArrayList<>();

        Usuario usuario = UsuarioFactory.get(usuarioId);
        all.add(usuario);
        Usuario usuario2 = UsuarioFactory.get(usuario2Id);
        all.add(usuario2);
        Usuario usuario3 = UsuarioFactory.get(usuario3Id);
        all.add(usuario3);
        List<Usuario> responseQuery = new ArrayList<>();
        responseQuery.add(usuario2);
        responseQuery.add(usuario3);

        List<DetalhesUsuarioResponse> responseEsperada = responseQuery.stream()
                .map(DetalharUsuarioMapper::toResponse)
                .collect(Collectors.toList());

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);
        when(usuarioRepository.findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(texto, texto)).thenReturn(all);

        List<DetalhesUsuarioResponse> response = tested.pesquisar(usuarioId, texto);

        verify(usuarioRepository, never()).procurarUsuarioPorNomeOuEmail(any(), any(), any());
        assertEquals(response.get(0).getId(), responseEsperada.get(0).getId());
        assertEquals(response.get(1).getId(), responseEsperada.get(1).getId());

        assertEquals(2, response.size());
    }
}
