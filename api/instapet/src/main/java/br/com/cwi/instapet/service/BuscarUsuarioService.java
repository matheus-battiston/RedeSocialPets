package br.com.cwi.instapet.service;

import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class BuscarUsuarioService {
    public static final String NAO_ENCONTRADO = "Usuario nao encontrado";
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario porId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, NAO_ENCONTRADO));
    }
}
