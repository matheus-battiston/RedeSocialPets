import { useState } from 'react';
import { aceitarAmizade } from '../../api/user/aceitarAmizade.api';
import useGlobalErro from '../../context/erro/erro.context';

export function useAceitarAmizade() {
  const [, setErro] = useGlobalErro();
  const [sucesso, setSucesso] = useState(false);

  async function aceitar(idPedido) {
    try {
      await aceitarAmizade(idPedido);
      setSucesso(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }

  return { aceitar, sucesso };
}
