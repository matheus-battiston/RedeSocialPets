package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesPostResponse;
import br.com.cwi.instapet.domain.Post;

public class DetalhesPostMapper {
    public static DetalhesPostResponse toResponse(Post post) {
        return DetalhesPostResponse.builder()
                .id(post.getId())
                .permissao(post.getPermissao())
                .legenda(post.getLegenda())
                .urlFoto(post.getUrlImagem())
                .build();
    }
}
