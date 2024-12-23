package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.PostFactory;
import br.com.cwi.instapet.repository.PostRepository;
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

public class BuscarPostServiceTest {

    @InjectMocks
    private BuscarPostService tested;
    @Mock
    private PostRepository repository;
    @Test
    @DisplayName("Deve retornar um post")
    void deveRetornarPedidoDeAmizader(){
        Post post = PostFactory.get();

        Mockito.when(repository.findById(post.getId())).thenReturn(Optional.of(post));
        Post retorno = tested.porId(post.getId());

        verify(repository).findById(post.getId());
        assertEquals(post, retorno);
    }

    @Test
    @DisplayName("Deve retornar erro quando nao encontrar pedido")
    void deveRetornarErro(){

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> tested.porId(1L));

        assertEquals("Post nao encontrado", exception.getReason());
    }
}
