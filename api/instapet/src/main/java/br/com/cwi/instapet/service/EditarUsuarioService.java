package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.request.EditarUsuarioRequest;
import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.cwi.instapet.mapper.DetalharUsuarioMapper.toResponse;

@Service
public class EditarUsuarioService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    public DetalhesUsuarioResponse editar(Long id, EditarUsuarioRequest request) {
        Usuario usuario = buscarUsuarioService.porId(id);

        usuario.setUrlFotoPerfil(request.getUrlFotoPerfil());
        usuario.setNome(request.getNome());
        if(Objects.nonNull(request.getApelido())){
            usuario.setApelido(request.getApelido());
        }

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }
}
