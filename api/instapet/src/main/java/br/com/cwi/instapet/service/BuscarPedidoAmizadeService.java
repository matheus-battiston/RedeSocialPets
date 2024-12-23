package br.com.cwi.instapet.service;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.repository.PedidoAmizadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class BuscarPedidoAmizadeService {
    public static final String NAO_EXISTE_PEDIDO = "Este pedido nao existe";
    @Autowired
    private PedidoAmizadeRepository pedidoAmizadeRepository;

    public PedidoAmizade porId(Long id){
        return pedidoAmizadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, NAO_EXISTE_PEDIDO));
    }
}
