package br.com.cwi.instapet.controller.request;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EditarUsuarioRequest {

    @NotBlank(message = "Deve ser informado um nome")
    private String nome;
    @URL(message = "URL informada é invalida")
    private String urlFotoPerfil;
    private String apelido;
}
