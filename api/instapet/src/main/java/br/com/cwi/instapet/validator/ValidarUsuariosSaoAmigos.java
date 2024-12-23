package br.com.cwi.instapet.validator;

import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidarUsuariosSaoAmigos {
    public static final String NAO_SAO_AMIGOS = "Usuarios nao sao amigos";

    public void validar(Usuario usuario, Usuario amigo) {
        if (!usuario.getAmigos().contains(amigo)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAO_SAO_AMIGOS);
        }
    }
}
