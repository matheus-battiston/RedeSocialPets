package br.com.cwi.instapet.validator;

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

public class ValidarUsuarioSaoAmigosTest {
    @InjectMocks
    private ValidarUsuariosSaoAmigos tested;
    @Test
    @DisplayName("Deve retornar uma exception quando ja forem amigos")
    void retornarExceptionQuandoAmigos(){
        Long idUsuario1 = 1L;
        Long idUsuario2 = 2L;

        Usuario usuario1 = UsuarioFactory.get(idUsuario1);
        Usuario usuario2 = UsuarioFactory.get(idUsuario2);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validar(usuario1, usuario2));

        assertEquals(ValidarUsuariosSaoAmigos.NAO_SAO_AMIGOS, exception.getReason());
    }
}
