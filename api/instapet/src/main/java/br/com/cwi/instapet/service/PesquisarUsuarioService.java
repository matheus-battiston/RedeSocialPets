package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.mapper.DetalharUsuarioMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PesquisarUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    public List<DetalhesUsuarioResponse> pesquisar(Long idUsuario, String texto) {
        Usuario usuario = buscarUsuarioService.porId(idUsuario);
        List<Usuario> usuariosPesquisados;

        System.out.println(usuario.getAmigos());

        if (usuario.getAmigos().size() > 0){
            usuariosPesquisados =  usuarioRepository.procurarUsuarioPorNomeOuEmail(usuario, usuario.getAmigos(), texto);
            usuariosPesquisados = usuariosPesquisados.stream()
                    .filter(usuarioPesquisado ->
                            usuarioPesquisado.getPedidosAmizadeEnviados().stream()
                                    .noneMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequisitado().getId(), usuario.getId())))
                    .filter(usuarioPesquisado -> usuarioPesquisado.getPedidosAmizadeRecebidos().stream()
                            .noneMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequerente().getId(), usuario.getId())))
                    .collect(Collectors.toList());
        } else {
            usuariosPesquisados = usuarioRepository
                    .findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(texto, texto).stream()
                    .filter(usuarioPesquisado -> usuario!=usuarioPesquisado)
                    .filter(usuarioPesquisado ->
                            usuarioPesquisado.getPedidosAmizadeEnviados().stream()
                                    .noneMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequisitado().getId(), usuario.getId())))
                    .filter(usuarioPesquisado -> usuarioPesquisado.getPedidosAmizadeRecebidos().stream()
                            .noneMatch(pedidoAmizade -> Objects.equals(pedidoAmizade.getRequerente().getId(), usuario.getId())))
                    .collect(Collectors.toList());
        }

        return usuariosPesquisados.stream()
                .map(DetalharUsuarioMapper::toResponse)
                .collect(Collectors.toList());

    }
}
