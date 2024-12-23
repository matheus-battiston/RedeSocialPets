package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class DetalharUsuarioServiceTest {
    @InjectMocks
    private DetalharUsuarioService tested;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve retornar o detalhamento de um usuario")
    void detalharUsuario(){
        Long idUsuario = 1L;
        Usuario usuario = UsuarioFactory.get(idUsuario);
        DetalhesUsuarioResponse esperado = DetalharUsuarioMapper.toResponse(usuario);

        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);

        DetalhesUsuarioResponse response = tested.detalhar(idUsuario);

        assertEquals(esperado.getApelido(), response.getApelido());
        assertEquals(esperado.getEmail(), response.getEmail());
        assertEquals(esperado.getNome(), response.getNome());
        assertEquals(esperado.getUrlFotoPerfil(), response.getUrlFotoPerfil());
        assertEquals(esperado.getId(), response.getId());
    }

    @Test
    @DisplayName("Nao deve detalhar usuario que nao existe")
    void naoDetalharUsuarioQueNaoExiste(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        assertThrows(ResponseStatusException.class, () -> {
            tested.detalhar(usuarioId);
        });
    }
}
