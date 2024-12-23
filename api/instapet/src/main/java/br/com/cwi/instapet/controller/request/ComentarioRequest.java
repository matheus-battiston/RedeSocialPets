package br.com.cwi.instapet.controller.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ComentarioRequest {

    @NotBlank(message = "Comentario deve ter algum conteudo")
    @Size(max = 500, message = "Comentario pode ter no maximo 500 caracteres")
    private String comentario;
}
