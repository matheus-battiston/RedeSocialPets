package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.PedidoAmizadeResponse;
import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.factories.PedidoAmizadeFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarPedidosAmizadeServiceTest {
    @InjectMocks
    private ListarPedidosAmizadeService tested;

    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve dar erro quando usuario nao existe")
    void deveRetornarErroQuandoUsuarioNaoExiste(){
        Long usuarioId = 1L;

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(usuarioId);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.listar(usuarioId);
        });
    }

    @Test
    @DisplayName("Deve retornar uma lista de pedidos de amizade")
    void deveRetornarUmaListaDePedidosDeAmizade(){
        Long idUsuario = 1L;
        Long idAmigoUm = 2L;
        Long idAmigoDois = 3L;
        Usuario usuario = UsuarioFactory.get(idUsuario);
        Usuario amigoUm = UsuarioFactory.get(idAmigoUm);
        Usuario amigoDois = UsuarioFactory.get(idAmigoDois);
        PedidoAmizade pedidoAmizade = PedidoAmizadeFactory.get(amigoUm, usuario);
        pedidoAmizade.setId(1L);
        PedidoAmizade pedidoAmizade2 = PedidoAmizadeFactory.get(amigoDois, usuario);
        pedidoAmizade2.setId(2L);

        usuario.getPedidosAmizadeRecebidos().add(pedidoAmizade);
        usuario.getPedidosAmizadeRecebidos().add(pedidoAmizade2);


        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);

        List<PedidoAmizadeResponse> responseList = tested.listar(idUsuario);

        assertEquals(responseList.get(0).getIdPedido(), 1L);
        assertEquals(responseList.get(1).getIdPedido(), 2L);
        assertEquals(responseList.size(), 2);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia por nao ter pedidos de amizade")
    void deveRetornarListaVaziaSemPedidosDeAmizade(){
        Long idUsuario = 1L;
        Usuario usuario = UsuarioFactory.get(idUsuario);

        when(buscarUsuarioService.porId(idUsuario)).thenReturn(usuario);

        List<PedidoAmizadeResponse> responseList = tested.listar(idUsuario);

        Assertions.assertTrue(responseList.isEmpty());
    }
}
