package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.PostHomeResponse;
import br.com.cwi.instapet.domain.Post;
import br.com.cwi.instapet.factories.UsuarioFactory;
import br.com.cwi.instapet.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.instapet.domain.PermissaoPost.PUBLICO;
import static br.com.cwi.instapet.factories.PostFactory.get;
import static br.com.cwi.instapet.mapper.DetalharUsuarioMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class ListarPostsMapperTest {

    @Test
    @DisplayName("Deve transformar um post em um post response para home")
    void postToHomePostResponse(){
        Long usuarioId = 1L;
        Usuario usuario = UsuarioFactory.get(usuarioId);
        String legenda = "Legenda test";
        String url = "url";
        Post post = get();
        post.setPermissao(PUBLICO);
        post.setLegenda(legenda);
        post.setUrlImagem(url);
        post.setUsuario(usuario);

        PostHomeResponse esperado = PostHomeResponse.builder()
                .legenda(post.getLegenda())
                .url(post.getUrlImagem())
                .usuarioResponse(toResponse(usuario))
                .curtido(usuario.getCurtidas().contains(post))
                .build();

        PostHomeResponse response = ListarPostsMapper.toResponse(post, usuario.getCurtidas());

        assertEquals(esperado.getLegenda(), response.getLegenda());
        assertEquals(esperado.getUsuarioResponse().getId(), response.getUsuarioResponse().getId());
        assertEquals(esperado.getUrl(), response.getUrl());
        assertEquals(esperado.isCurtido(), response.isCurtido());

    }
}
