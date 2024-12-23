package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.controller.response.PedidoAmizadeResponse;
import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.instapet.mapper.PedidoAmizadeMapper.toEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class PedidoAmizadeMapperTest {
    @Test
    @DisplayName("Deve criar um pedido")
    void criarpeDIDO(){
        Long usuarioRequerenteId = 1L;
        Long usuarioRequistadoId = 1L;

        Usuario usuarioRequerente = UsuarioFactory.get(usuarioRequerenteId);
        Usuario usuarioRequisitado = UsuarioFactory.get(usuarioRequistadoId);

        PedidoAmizade esperado = PedidoAmizade.builder()
                .requisitado(usuarioRequisitado)
                .requerente(usuarioRequerente)
                .build();

        PedidoAmizade response = toEntity(usuarioRequerente, usuarioRequisitado);

        assertEquals(esperado.getRequerente().getId(), response.getRequerente().getId());
        assertEquals(esperado.getRequisitado().getId(), response.getRequisitado().getId());
    }

    @Test
    @DisplayName("Deve transformar um pedido de amizade em response")
    void entityToResponse(){
        Long usuarioRequerenteId = 1L;
        Long usuarioRequistadoId = 1L;
        Long idPedido = 1L;

        Usuario usuarioRequerente = UsuarioFactory.get(usuarioRequerenteId);
        Usuario usuarioRequisitado = UsuarioFactory.get(usuarioRequistadoId);

        PedidoAmizade pedidoAmizade = PedidoAmizade.builder()
                .id(idPedido)
                .requisitado(usuarioRequisitado)
                .requerente(usuarioRequerente)
                .build();

        DetalhesUsuarioResponse detalheUsuarioEsperado = DetalharUsuarioMapper.toResponse(usuarioRequerente);
        PedidoAmizadeResponse esperado = PedidoAmizadeResponse.builder()
                .idPedido(idPedido)
                .requerente(detalheUsuarioEsperado)
                .build();

        PedidoAmizadeResponse response = PedidoAmizadeMapper.toResponse(pedidoAmizade);

        assertEquals(response.getIdPedido(), esperado.getIdPedido());
        assertEquals(response.getRequerente().getId(), esperado.getRequerente().getId());
    }
}
