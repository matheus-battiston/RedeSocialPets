package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.request.ComentarioRequest;
import br.com.cwi.instapet.controller.response.ComentarioResponse;
import br.com.cwi.instapet.domain.Comentario;

public class ComentarioMapper {
    public static Comentario toEntity(ComentarioRequest request) {
        return Comentario.builder()
                .comentario(request.getComentario())
                .build();
    }

    public static ComentarioResponse toResponse(Comentario comentario) {
        return ComentarioResponse.builder()
                .comentario(comentario.getComentario())
                .horario(comentario.getHorario())
                .usuario(DetalharUsuarioMapper.toResponse(comentario.getUsuario()))
                .build();
    }
}
