package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesPostResponse;
import br.com.cwi.instapet.domain.PermissaoPost;
import br.com.cwi.instapet.mapper.DetalhesPostMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarPostsDeUmUsuarioService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    public List<DetalhesPostResponse> listar(Long idUsuarioLogado, Long idUsuario) {
        Usuario usuarioLogado = buscarUsuarioService.porId(idUsuarioLogado);
        Usuario usuario = buscarUsuarioService.porId(idUsuario);

        if (usuario.getAmigos().contains(usuarioLogado)){
            return usuario.getPosts().stream()
                    .map(DetalhesPostMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return usuario.getPosts().stream()
                .filter(post -> post.getPermissao() == PermissaoPost.PUBLICO)
                .map(DetalhesPostMapper::toResponse)
                .collect(Collectors.toList());
    }
}
