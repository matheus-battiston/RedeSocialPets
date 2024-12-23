package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.security.domain.Usuario;

public class DetalharUsuarioMapper {
    public static DetalhesUsuarioResponse toResponse(Usuario entity){

        return DetalhesUsuarioResponse.builder()
                .apelido(entity.getApelido())
                .email(entity.getEmail())
                .nome(entity.getNome())
                .urlFotoPerfil(entity.getUrlFotoPerfil())
                .id(entity.getId())
                .build();
    }
}
