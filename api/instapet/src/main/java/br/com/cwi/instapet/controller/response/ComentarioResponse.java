package br.com.cwi.instapet.controller.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ComentarioResponse {
    private DetalhesUsuarioResponse usuario;
    private LocalDateTime horario;
    private String comentario;
}
