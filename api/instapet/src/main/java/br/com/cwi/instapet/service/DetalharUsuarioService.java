package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.instapet.mapper.DetalharUsuarioMapper.toResponse;

@Service
public class DetalharUsuarioService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    public DetalhesUsuarioResponse detalhar(Long id) {
        Usuario usuario = buscarUsuarioService.porId(id);

        return toResponse(usuario);
    }
}
