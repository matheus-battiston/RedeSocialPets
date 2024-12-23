package br.com.cwi.instapet.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PedidoAmizadeResponse {
    private DetalhesUsuarioResponse requerente;
    private Long idPedido;
}
