package br.com.cwi.instapet.service;

import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import br.com.cwi.instapet.validator.ValidarUsuariosSaoAmigos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesfazerAmizadeService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private ValidarUsuariosSaoAmigos validarUsuariosSaoAmigos;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void desfazer(Long idUsuarioLogado, Long idAmigo) {
        Usuario usuario = buscarUsuarioService.porId(idUsuarioLogado);
        Usuario amigo = buscarUsuarioService.porId(idAmigo);

        validarUsuariosSaoAmigos.validar(usuario, amigo);

        usuario.removerAmigo(amigo);

        usuarioRepository.save(usuario);
    }
}
