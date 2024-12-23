package br.com.cwi.instapet.validator;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class ValidatorCurtidaTest {
    @InjectMocks
    private ValidatorCurtida tested;
    @Test
    @DisplayName("Deve retornar uma exception quando post estiver na lista de curtidos")
    void retornarExceptionQuandoJaCurtiu(){
        Post post = PostFactory.get();
        List<Post> curtidas = new ArrayList<>();
        curtidas.add(post);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validarNaoCurtiu(curtidas, post));

        assertEquals(ValidatorCurtida.JA_CURTIU, exception.getReason());
    }

    @Test
    @DisplayName("Deve retornar uma exception quando post nao estiver na lista de curtidos")
    void retornarExceptionQuandoNaoCurtiu(){
        Post post = PostFactory.get();
        List<Post> curtidas = new ArrayList<>();

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.validarJaCurtiu(curtidas, post));

        assertEquals(ValidatorCurtida.NAO_CURTIU, exception.getReason());
    }
}
