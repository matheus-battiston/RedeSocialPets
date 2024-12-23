package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.factories.PedidoAmizadeFactory;
import br.com.cwi.instapet.repository.PedidoAmizadeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BuscarPedidoAmizadeServiceTest {

    @InjectMocks
    private BuscarPedidoAmizadeService tested;
    @Mock
    private PedidoAmizadeRepository repository;
    @Test
    @DisplayName("Deve retornar um pedido de amizade")
    void deveRetornarPedidoDeAmizader(){
        PedidoAmizade pedidoAmizade = PedidoAmizadeFactory.PedidoAmizadeComUsers();

        Mockito.when(repository.findById(pedidoAmizade.getId())).thenReturn(Optional.of(pedidoAmizade));
        PedidoAmizade retorno = tested.porId(pedidoAmizade.getId());

        verify(repository).findById(pedidoAmizade.getId());
        assertEquals(pedidoAmizade, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar pedido")
    void deveRetornarErro(){

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Este pedido nao existe", exception.getReason());
    }
}
