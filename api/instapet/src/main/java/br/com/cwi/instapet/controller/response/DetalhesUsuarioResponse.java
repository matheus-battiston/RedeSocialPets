package br.com.cwi.instapet.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(NON_NULL)
public class DetalhesUsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String urlFotoPerfil;
    private String apelido;
}
