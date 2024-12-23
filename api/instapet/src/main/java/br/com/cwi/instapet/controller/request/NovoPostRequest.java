package br.com.cwi.instapet.controller.request;

import br.com.cwi.instapet.domain.PermissaoPost;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NovoPostRequest {
    @NotBlank(message = "Deve ser informado uma url de foto")
    @URL(message = "URL deve ser valida")
    @Size(max = 1000, message = "URL nao pode passar de 1000 caracteres")
    private String urlFoto;

    @Size(max = 2000, message = "Legenda pode ter no maximo 2000 caracteres")
    private String legenda;

    @NotNull(message = "Deve ser definido uma politica de visualizaçao")
    private PermissaoPost permissao;
}
