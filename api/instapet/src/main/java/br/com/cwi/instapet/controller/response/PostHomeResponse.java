package br.com.cwi.instapet.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostHomeResponse {
    private String legenda;
    private String url;
    private DetalhesUsuarioResponse usuarioResponse;
    private boolean curtido;
    private Integer numeroLikes;
    private Long id;
}
