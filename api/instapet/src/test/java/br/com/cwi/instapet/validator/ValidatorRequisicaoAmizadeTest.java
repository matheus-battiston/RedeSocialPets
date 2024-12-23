package br.com.cwi.instapet.validator;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.factories.PedidoAmizadeFactory;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class ValidatorRequisicaoAmizadeTest {
    @InjectMocks
    private ValidatorRequisicaoAmizade tested;

    @Test
    @DisplayName("Deve retornar exception quando passado o mesmo usuario")
    void deveRetornarExceptionQuandoPassadoMesmoUsuario(){
        Long idUsuario = 1L;
        Usuario usuario = UsuarioFactory.get(idUsuario);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, usuario));

        assertEquals(ValidatorRequisicaoAmizade.PEDIDO_PARA_PROPRIO_USUARIO, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar exception quando usuarios ja sao amigos")
    void deveRetornarExceptionQuandoSaoAmigos(){
        Long idUsuario = 1L;
        Long idUsuario2 = 2L;
        Usuario usuario = UsuarioFactory.get(idUsuario);
        Usuario usuario2 = UsuarioFactory.get(idUsuario2);

        usuario.addAmigo(usuario2);
        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, usuario2));

        assertEquals(ValidatorRequisicaoAmizade.JA_SAO_AMIGOS, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar exception quando ja existe pedido de amizade entre os dois")
    void deveRetornarExceptionQuandoExistePedidoPendenteEntreOsDois(){
        Long idUsuario = 1L;
        Long idUsuario2 = 2L;
        Usuario usuario = UsuarioFactory.get(idUsuario);
        Usuario usuario2 = UsuarioFactory.get(idUsuario2);

        PedidoAmizade pedidoAmizade = PedidoAmizadeFactory.get(usuario, usuario2);
        usuario.getPedidosAmizadeEnviados().add(pedidoAmizade);
        usuario2.getPedidosAmizadeRecebidos().add(pedidoAmizade);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, usuario2));

        assertEquals(ValidatorRequisicaoAmizade.PEDIDO_EXISTENTE, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar exception quando ja existe pedido de amizade entre os dois")
    void deveTerExceptionQuandoOUsuario2QueFezOPedido(){
        Long idUsuario = 1L;
        Long idUsuario2 = 2L;
        Usuario usuario = UsuarioFactory.get(idUsuario);
        Usuario usuario2 = UsuarioFactory.get(idUsuario2);

        PedidoAmizade pedidoAmizade = PedidoAmizadeFactory.get(usuario2, usuario);
        usuario.getPedidosAmizadeRecebidos().add(pedidoAmizade);
        usuario2.getPedidosAmizadeEnviados().add(pedidoAmizade);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario, usuario2));

        assertEquals(ValidatorRequisicaoAmizade.PEDIDO_EXISTENTE, exception.getReason());
    }
}
