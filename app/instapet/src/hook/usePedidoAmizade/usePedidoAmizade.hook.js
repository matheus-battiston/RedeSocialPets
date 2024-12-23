import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { pedidoAmizade } from '../../api/user/novoPedidoAmizade.api';

export function useFazerPedido() {
  const [response, setResponse] = useState(false);
  const [, setErro] = useGlobalErro();

  async function fazerPedido(idAmigo) {
    try {
      await pedidoAmizade(idAmigo);
      setResponse(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { response, fazerPedido, setResponse };
}
