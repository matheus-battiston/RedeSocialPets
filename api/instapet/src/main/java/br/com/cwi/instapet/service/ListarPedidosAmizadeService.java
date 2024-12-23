package br.com.cwi.instapet.service;

import br.com.cwi.instapet.controller.response.PedidoAmizadeResponse;
import br.com.cwi.instapet.mapper.PedidoAmizadeMapper;
import br.com.cwi.instapet.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarPedidosAmizadeService {
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    public List<PedidoAmizadeResponse> listar(long idUsuarioLogado) {
        Usuario usuario = buscarUsuarioService.porId(idUsuarioLogado);
        return usuario.getPedidosAmizadeRecebidos().stream()
                .map(PedidoAmizadeMapper::toResponse)
                .collect(Collectors.toList());
    }
}
