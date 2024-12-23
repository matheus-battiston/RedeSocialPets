package br.com.cwi.instapet.factories;

import br.com.cwi.instapet.domain.Comentario;
import br.com.cwi.instapet.security.domain.Usuario;

import java.time.LocalDateTime;

public class ComentarioFactory {
    public static Comentario get(Long idComentario){
        return Comentario.builder()
                .id(idComentario)
                .comentario(idComentario + "Comentario")
                .horario(LocalDateTime.now())
                .usuario(new Usuario())
                .build();
    }
}
