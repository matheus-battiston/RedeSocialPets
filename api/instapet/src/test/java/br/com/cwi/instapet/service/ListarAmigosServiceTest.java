package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ListarAmigosServiceTest {
    @InjectMocks
    private ListarAmigosService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve dar erro quando usuario nao existe")
    void deveDarErroComUsuarioInexistente(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.listar(usuarioId, null);
        });

    }
    @Test
    @DisplayName("Deve retornar lista de amigos sem filtro quando nao tiver texto")
    void listaSemFiltros(){
        Long usuarioId = 1L;
        Long amigoUmId = 2L;
        Long amigoDoisId = 3L;

        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigoUm = UsuarioFactory.get(amigoUmId);
        Usuario amigoDois = UsuarioFactory.get(amigoDoisId);

        usuario.getAmigos().add(amigoUm);
        usuario.getAmigos().add(amigoDois);

        List<DetalhesUsuarioResponse> respostaEsperada = usuario.getAmigos().stream()
                .map(DetalharUsuarioMapper::toResponse)
                .collect(Collectors.toList());

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        List<DetalhesUsuarioResponse> response = tested.listar(usuarioId, null);

        assertEquals(response.get(0).getId(), respostaEsperada.get(0).getId());
        assertEquals(response.get(1).getId(), respostaEsperada.get(1).getId());
    }

    @Test
    @DisplayName("Deve retorna uma lista filtrada de amigos utilizando email")
    void deveRetornarListaFiltrada(){
        Long usuarioId = 1L;
        Long amigoUmId = 2L;
        Long amigoDoisId = 3L;
        String textoFiltro = "cwi";

        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigoUm = UsuarioFactory.get(amigoUmId);
        Usuario amigoDois = UsuarioFactory.get(amigoDoisId);
        amigoUm.setEmail("emailSemCwi");
        amigoDois.setEmail("amigoDois@cwi.com.br");

        usuario.getAmigos().add(amigoUm);
        usuario.getAmigos().add(amigoDois);

        List<DetalhesUsuarioResponse> respostaEsperada = new ArrayList<>();
        respostaEsperada.add(DetalharUsuarioMapper.toResponse(amigoDois));

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        List<DetalhesUsuarioResponse> response = tested.listar(usuarioId, textoFiltro);

        assertEquals(response.get(0).getId(), respostaEsperada.get(0).getId());
        assertEquals(response.size() , 1);
    }

    @Test
    @DisplayName("Deve retorna uma lista filtrada de amigos utilizando nome")
    void deveRetornarListaFiltradaPorNome(){
        Long usuarioId = 1L;
        Long amigoUmId = 2L;
        Long amigoDoisId = 3L;
        String textoFiltro = "nome de teste";

        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigoUm = UsuarioFactory.get(amigoUmId);
        Usuario amigoDois = UsuarioFactory.get(amigoDoisId);
        amigoUm.setEmail("emailSemCwi");
        amigoDois.setEmail("amigoDois@cwi.com.br");
        amigoDois.setNome("nome de teste");


        usuario.getAmigos().add(amigoUm);
        usuario.getAmigos().add(amigoDois);

        List<DetalhesUsuarioResponse> respostaEsperada = new ArrayList<>();
        respostaEsperada.add(DetalharUsuarioMapper.toResponse(amigoDois));

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        List<DetalhesUsuarioResponse> response = tested.listar(usuarioId, textoFiltro);

        assertEquals(response.get(0).getId(), respostaEsperada.get(0).getId());
        assertEquals(response.size() , 1);
    }

    @Test
    @DisplayName("Deve retorna uma lista vazia caso nao tenha nenhum usuario que possa ser filtrado")
    void deveRetornarListaVaziaPorNaoConseguirFiltrar(){
        Long usuarioId = 1L;
        Long amigoUmId = 2L;
        Long amigoDoisId = 3L;
        String textoFiltro = "ABABABABABABABA";

        Usuario usuario = UsuarioFactory.get(usuarioId);
        Usuario amigoUm = UsuarioFactory.get(amigoUmId);
        Usuario amigoDois = UsuarioFactory.get(amigoDoisId);
        amigoUm.setEmail("emailSemCwi");
        amigoDois.setEmail("amigoDois@cwi.com.br");
        amigoDois.setNome("nome de teste");


        usuario.getAmigos().add(amigoUm);
        usuario.getAmigos().add(amigoDois);

        when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        List<DetalhesUsuarioResponse> response = tested.listar(usuarioId, textoFiltro);


        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar um erro se o usuario nao existir")
    void deveRetornarErroSeOUsuarioNaoExistir(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.listar(usuarioId, null);
        });

    }
}
