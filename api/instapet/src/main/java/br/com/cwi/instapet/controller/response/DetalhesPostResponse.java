package br.com.cwi.instapet.controller.response;

import br.com.cwi.instapet.domain.PermissaoPost;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DetalhesPostResponse {
    private PermissaoPost permissao;
    private String urlFoto;
    private String legenda;
    private Long id;
}
