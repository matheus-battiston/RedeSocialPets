package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.PostHomeResponse;
import br.com.cwi.instapet.domain.Post;

import java.util.List;

public class ListarPostsMapper {
    public static PostHomeResponse toResponse(Post post, List<Post> curtidasDoUsuario) {
        return PostHomeResponse.builder()
                .id(post.getId())
                .legenda(post.getLegenda())
                .url(post.getUrlImagem())
                .usuarioResponse(DetalharUsuarioMapper.toResponse(post.getUsuario()))
                .curtido(curtidasDoUsuario.contains(post))
                .numeroLikes(post.getNumeroLikes())
                .build();
    }
}
