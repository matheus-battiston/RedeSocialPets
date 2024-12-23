package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.factories.PedidoAmizadeFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.repository.PedidoAmizadeRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import br.com.cwi.instapet.validator.ValidatorRequisicaoAmizade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdicionarAmigoServiceTest {
    @InjectMocks
    private AdicionarAmigoService tested;

    @Mock
    private BuscarPedidoAmizadeService buscarPedidoAmizadeService;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Mock
    private ValidatorRequisicaoAmizade validatorRequisicaoAmizade;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PedidoAmizadeRepository pedidoAmizadeRepository;

    @Test
    @DisplayName("Deve enviar um pedido de amizade")
    void deveEnviarPedidoParaUmaPessoa(){

        Long idRequerente = 1L;
        Long idRequisitado = 2L;

        Usuario requerente = UsuarioFactory.get(idRequerente);
        Usuario requisitado = UsuarioFactory.get(idRequisitado);
        PedidoAmizade pedido = PedidoAmizadeFactory.get(requerente, requisitado);

        when(buscarUsuarioService.porId(idRequerente)).thenReturn(requerente);
        when(buscarUsuarioService.porId(idRequisitado)).thenReturn(requisitado);

        tested.adicionar(idRequerente, idRequisitado);

        verify(validatorRequisicaoAmizade).validar(requerente, requisitado);
        verify(pedidoAmizadeRepository).save(pedido);

        assertEquals(pedido.getRequisitado(), requisitado);
        assertEquals(pedido.getRequerente(), requerente);
    }

    @Test
    @DisplayName("Nao deve adicionar um amigo quando um dos dois usuarios nao existirem")
    void naoAdicionarAmigoSemRequerente(){

        doThrow(ResponseStatusException.class).when(buscarUsuarioService).porId(10L);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.adicionar(10L, 1L);
        });

        verify(pedidoAmizadeRepository, never()).save(any());

    }

    @Test
    @DisplayName("Nao deve adicionar o mesmo usuario como amigo")
    void naoAdicionarSiMesmoComoAmigo(){
        Long idRequerente = 1L;
        Long idRequisitado = 1L;

        Usuario requerente = UsuarioFactory.get(idRequerente);
        Usuario requisitado = UsuarioFactory.get(idRequisitado);

        when(buscarUsuarioService.porId(idRequerente)).thenReturn(requerente);
        when(buscarUsuarioService.porId(idRequisitado)).thenReturn(requisitado);
        doThrow(ResponseStatusException.class).when(validatorRequisicaoAmizade).validar(requerente, requisitado);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.adicionar(idRequerente, idRequisitado);
        });

        verify(pedidoAmizadeRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve adicionar um amigo")
    void deveAdicionarUmAmigo(){
        Long idRequerente = 50L;
        Long idRequisitado = 51L;
        Long idPedidoAmizade = 102L;

        Usuario requerente = UsuarioFactory.get(idRequerente);
        Usuario requisitado = UsuarioFactory.get(idRequisitado);

        PedidoAmizade pedidoAmizade = PedidoAmizadeFactory.getComId(requerente, requisitado, idPedidoAmizade);

        List<Usuario> listaDeAmigosEsperadaRequerente = requerente.getAmigos();
        listaDeAmigosEsperadaRequerente.add(requisitado);

        List<Usuario> listaDeAmigosEsperadaRequisitado = requisitado.getAmigos();
        listaDeAmigosEsperadaRequisitado.add(requerente);

        when(buscarPedidoAmizadeService.porId(idPedidoAmizade)).thenReturn(pedidoAmizade);
        when(buscarUsuarioService.porId(idRequisitado)).thenReturn(requisitado);

        tested.aceitar(idRequisitado, idPedidoAmizade);

        verify(pedidoAmizadeRepository).deleteById(idPedidoAmizade);
        verify(usuarioRepository).save(requisitado);

        assertEquals(requerente.getAmigos(), listaDeAmigosEsperadaRequerente);
        assertEquals(requisitado.getAmigos(), listaDeAmigosEsperadaRequisitado);
    }

    @Test
    @DisplayName("Nao deve adicionar amigo de um pedido inexistente")
    void naoAdicionarAmizadePedidoInexistente(){
        Long idPedido = 10L;
        Long idUsuario = 1L;
        doThrow(ResponseStatusException.class).when(buscarPedidoAmizadeService).porId(idPedido);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            tested.aceitar(idUsuario, idPedido);
        });

        verify(usuarioRepository, never()).deleteById(any());
        verify(usuarioRepository, never()).save(any());

    }

    @Test
    @DisplayName("Deve recusar um pedido de amizade")
    void recusarPedido(){
        Long usuarioId = 1L;
        Long idPedido = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);

        Mockito.when(buscarUsuarioService.porId(usuarioId)).thenReturn(usuario);

        tested.recusar(usuarioId, idPedido);

        verify(pedidoAmizadeRepository).deleteById(idPedido);
        verify(usuarioRepository).save(usuario);
    }
}
