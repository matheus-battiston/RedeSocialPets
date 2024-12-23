package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.request.NovoPostRequest;
import br.com.cwi.instapet.domain.PermissaoPost;
import br.com.cwi.instapet.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class PostMappertTest {
    @Test
    @DisplayName("Deve transformar um request em entity")
    void requestToEntity(){
        String legenda = "Legenda teste";
        String urlImagem = "url";
        PermissaoPost permissaoPost = PermissaoPost.PUBLICO;
        NovoPostRequest novoPost = new NovoPostRequest(urlImagem, legenda, permissaoPost);

        Post esperado = Post.builder()
                .permissao(permissaoPost)
                .urlImagem(urlImagem)
                .legenda(legenda)
                .build();

        Post response = PostMapper.toEntity(novoPost);

        assertEquals(esperado.getPermissao(), response.getPermissao());
        assertEquals(esperado.getUrlImagem(), response.getUrlImagem());
        assertEquals(esperado.getLegenda(), response.getLegenda());


    }
}
