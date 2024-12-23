package br.com.cwi.instapet.validator;

import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ValidatorRequisicaoAmizade {
    public static final String JA_SAO_AMIGOS = "Ja sao amigos";
    public static final String PEDIDO_EXISTENTE = "Ja existe um pedido de amizade pendente";
    public static final String PEDIDO_PARA_PROPRIO_USUARIO = "Nao é possivel adicionar o proprio perfil";
    public void validar(Usuario usuarioRequerente, Usuario usuarioRequisitado) {

        if (Objects.equals(usuarioRequerente.getId(), usuarioRequisitado.getId())){
            throw new ResponseStatusException(BAD_REQUEST, PEDIDO_PARA_PROPRIO_USUARIO);
        }

        if(usuarioRequerente.getAmigos().contains(usuarioRequisitado)){
            throw new ResponseStatusException(BAD_REQUEST, JA_SAO_AMIGOS);
        }

        if (usuarioRequerente.getPedidosAmizadeEnviados().stream()
                .anyMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequisitado().getId(), usuarioRequisitado.getId()))) {
            throw new ResponseStatusException(BAD_REQUEST, PEDIDO_EXISTENTE);
        }

        if (usuarioRequisitado.getPedidosAmizadeEnviados().stream()
                .anyMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequisitado().getId(), usuarioRequerente.getId()))) {
            throw new ResponseStatusException(BAD_REQUEST, PEDIDO_EXISTENTE);
        }
    }
}
