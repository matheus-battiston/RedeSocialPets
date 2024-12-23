import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { recusarAmizade } from '../../api/user/recusarAmizade.api';

export function useRecusarAmizade() {
  const [, setErro] = useGlobalErro();
  const [sucessoRecusar, setSucessoRecusar] = useState(false);

  async function recusar(idPedido) {
    try {
      await recusarAmizade(idPedido);
      setSucessoRecusar(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }

  return { recusar, sucessoRecusar };
}
