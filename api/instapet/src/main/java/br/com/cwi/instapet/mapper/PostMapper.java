package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.request.NovoPostRequest;
import br.com.cwi.instapet.domain.Post;

public class PostMapper {
    public static Post toEntity(NovoPostRequest request) {
        return Post.builder()
                .legenda(request.getLegenda())
                .urlImagem(request.getUrlFoto())
                .permissao(request.getPermissao())
                .build();
    }
}
