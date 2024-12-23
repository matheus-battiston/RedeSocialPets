package br.com.cwi.instapet.security.mapper;

import br.com.cwi.instapet.security.controller.request.UsuarioRequest;
import br.com.cwi.instapet.security.controller.response.UsuarioResponse;
import br.com.cwi.instapet.security.domain.Usuario;

import static java.util.Objects.nonNull;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setDataNascimento(request.getDataNascimento());

        if (nonNull(request.getApelido())){
            entity.setApelido(request.getApelido());
        }

        if (nonNull(request.getUrlFotoPerfil())){
            entity.setUrlFotoPerfil(request.getUrlFotoPerfil());
        }

        return entity;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        return UsuarioResponse.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .build();
    }
}
