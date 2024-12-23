package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.repository.PedidoAmizadeRepository;
import br.com.cwi.instapet.security.domain.Usuario;
import br.com.cwi.instapet.security.repository.UsuarioRepository;
import br.com.cwi.instapet.validator.ValidatorRequisicaoAmizade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.instapet.mapper.PedidoAmizadeMapper.toEntity;

@Service
public class AdicionarAmigoService {

    @Autowired
    private BuscarPedidoAmizadeService buscarPedidoAmizadeService;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private ValidatorRequisicaoAmizade validatorRequisicaoAmizade;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoAmizadeRepository pedidoAmizadeRepository;

    @Transactional
    public void adicionar(Long requerente, Long requisitado) {
        Usuario usuarioRequerente = buscarUsuarioService.porId(requerente);
        Usuario usuarioRequisitado = buscarUsuarioService.porId(requisitado);

        validatorRequisicaoAmizade.validar(usuarioRequerente, usuarioRequisitado);

        PedidoAmizade pedidoAmizade = toEntity(usuarioRequerente, usuarioRequisitado);
        usuarioRequerente.addPedidoAmizade(pedidoAmizade);

        pedidoAmizadeRepository.save(pedidoAmizade);
    }

    @Transactional
    public void aceitar(Long idUsuarioRequisitado, Long idPedido) {
        PedidoAmizade pedidoAmizade = buscarPedidoAmizadeService.porId(idPedido);

        Usuario usuarioRequisitado = buscarUsuarioService.porId(idUsuarioRequisitado);
        Usuario usuarioRequerente = pedidoAmizade.getRequerente();

        usuarioRequisitado.addAmigo(usuarioRequerente);
        pedidoAmizadeRepository.deleteById(idPedido);

        usuarioRepository.save(usuarioRequisitado);

    }

    @Transactional
    public void recusar(Long idUsuarioRequisitado, Long idPedido) {

        Usuario usuarioRequisitado = buscarUsuarioService.porId(idUsuarioRequisitado);

        pedidoAmizadeRepository.deleteById(idPedido);

        usuarioRepository.save(usuarioRequisitado);

    }
}
