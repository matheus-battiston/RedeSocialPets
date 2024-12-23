package br.com.cwi.instapet.factories;

import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.security.domain.Usuario;

public class PedidoAmizadeFactory {
    public static PedidoAmizade get(Usuario requerente, Usuario requisitado){
        PedidoAmizade pedido = new PedidoAmizade();
        pedido.setRequerente(requerente);
        pedido.setRequisitado(requisitado);

        return pedido;
    }
    
    public static PedidoAmizade getComId(Usuario requerente,Usuario requisitado, Long id){
        PedidoAmizade pedidoAmizade = get(requerente, requisitado);
        pedidoAmizade.setId(id);
        return pedidoAmizade;
    }

    public static PedidoAmizade PedidoAmizadeComUsers(){
        Long idUm = 1L;
        Long idDois = 2L;
        Long idPedido = 1L;
        Usuario userUm = UsuarioFactory.get(idUm);
        Usuario userDois = UsuarioFactory.get(idDois);

        return getComId(userUm, userDois, idPedido);
    }
}
