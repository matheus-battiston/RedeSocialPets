package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class ListarAmigosService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    public List<DetalhesUsuarioResponse> listar(Long idUsuarioLogado, String texto) {
        Usuario usuario = buscarUsuarioService.porId(idUsuarioLogado);
        List<Usuario> amigos = usuario.getAmigos();

        if (nonNull(texto)){
            return amigos.stream()
                    .filter(amigo -> amigo.getNome().contains(texto) || amigo.getEmail().contains(texto))
                    .map(DetalharUsuarioMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return amigos.stream()
                .map(DetalharUsuarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}
