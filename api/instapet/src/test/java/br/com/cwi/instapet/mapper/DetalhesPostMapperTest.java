package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesPostResponse;
import br.com.cwi.instapet.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.instapet.controller.response.DetalhesPostResponse.builder;
import static br.com.cwi.instapet.domain.PermissaoPost.PUBLICO;
import static br.com.cwi.instapet.factories.PostFactory.get;

@ExtendWith(MockitoExtension.class)

public class DetalhesPostMapperTest {
    @Test
    @DisplayName("Deve transformar uma entidade Post em response")
    void postToResponse(){
        String legenda = "Legenda test";
        String url = "url";
        Post post = get();
        post.setPermissao(PUBLICO);
        post.setLegenda(legenda);
        post.setUrlImagem(url);

        DetalhesPostResponse esperado = builder()
                .urlFoto(url)
                .legenda(legenda)
                .permissao(PUBLICO)
                .build();

        DetalhesPostResponse response = DetalhesPostMapper.toResponse(post);

        Assertions.assertEquals(response.getLegenda(), esperado.getLegenda());
        Assertions.assertEquals(response.getPermissao(), esperado.getPermissao());
        Assertions.assertEquals(response.getUrlFoto(), esperado.getUrlFoto());
    }
}
