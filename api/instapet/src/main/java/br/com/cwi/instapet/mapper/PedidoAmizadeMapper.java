package br.com.cwi.instapet.mapper;

import br.com.cwi.instapet.controller.response.DetalhesUsuarioResponse;
import br.com.cwi.instapet.controller.response.PedidoAmizadeResponse;
import br.com.cwi.instapet.domain.PedidoAmizade;
import br.com.cwi.instapet.security.domain.Usuario;

public class PedidoAmizadeMapper {
    public static PedidoAmizade toEntity(Usuario usuarioRequerente, Usuario usuarioRequisitado) {
        return PedidoAmizade.builder()
                .requerente(usuarioRequerente)
                .requisitado(usuarioRequisitado)
                .build();
    }

    public static PedidoAmizadeResponse toResponse(PedidoAmizade entity){
        DetalhesUsuarioResponse usuarioResponse = DetalharUsuarioMapper.toResponse(entity.getRequerente());

        return PedidoAmizadeResponse.builder()
                .idPedido(entity.getId())
                .requerente(usuarioResponse)
                .build();
    }
}
