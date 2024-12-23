package br.com.cwi.instapet.validator;

import br.com.cwi.instapet.domain.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class ValidatorCurtida {
    public static final String JA_CURTIU = "Post ja foi curtido";
    public static final String NAO_CURTIU = "Post nao havia sido curtido";
    public void validarNaoCurtiu(List<Post> curtidas, Post post) {

        if (curtidas.contains(post)){
            throw new ResponseStatusException(BAD_REQUEST, JA_CURTIU);
        }
    }

    public void validarJaCurtiu(List<Post> curtidas, Post post) {
        if (!curtidas.contains(post)){
            throw new ResponseStatusException(BAD_REQUEST, NAO_CURTIU);
        }
    }
}
