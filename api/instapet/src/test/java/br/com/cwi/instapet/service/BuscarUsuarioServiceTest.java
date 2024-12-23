package br.com.cwi.instapet.service;

import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.instapet.factories.UsuarioFactory.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class BuscarUsuarioServiceTest {
    @InjectMocks
    private BuscarUsuarioService tested;
    @Mock
    private UsuarioRepository repository;
    @Test
    @DisplayName("Deve retornar um post")
    void deveRetornarPedidoDeAmizader(){
        Usuario usuario = get(1L);

        when(repository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        Usuario retorno = tested.porId(usuario.getId());

        verify(repository).findById(usuario.getId());
        assertEquals(usuario, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar pedido")
    void deveRetornarErro(){

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Usuario nao encontrado", exception.getReason());
    }
}
