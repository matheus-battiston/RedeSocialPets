package br.com.cwi.instapet.security.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class UsuarioRequest {

    @NotBlank(message = "Nome deve ser preenchido")
    @Size(min = 3, max = 255, message = "Numero de caracteres do nome deve ser no minimo 3 e maximo 255")
    private String nome;

    @NotNull(message = "Email deve ser preenchido")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "Deve ser definido uma senha")
    private String senha;

    @Size(min = 3, max = 50, message = "Apelido invalido")
    private String apelido;

    @NotNull(message = "Deve ser informado uma data de nascimento")
    private LocalDate dataNascimento;

    @URL(message = "Deve ser uma url valida")
    private String urlFotoPerfil;
}
