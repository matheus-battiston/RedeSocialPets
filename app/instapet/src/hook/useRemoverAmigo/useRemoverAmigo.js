import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { removerAmigo } from '../../api/user/removerAmigo.api';
export function useRemoverAmigo() {
  const [responseRemover, setResponseRemover] = useState(false);
  const [, setErro] = useGlobalErro();

  async function removerAmigoFunc(idAmigo) {
    try {
      await removerAmigo(idAmigo);
      setResponseRemover(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { responseRemover, removerAmigoFunc, setResponseRemover };
}
