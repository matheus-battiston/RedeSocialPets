import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { removerCurtida } from '../../api/posts/removerCurtida.api';

export function useRemoverCurtida() {
  const [responseRemover, setResponseRemover] = useState(false);
  const [, setErro] = useGlobalErro();

  async function removerCurtidaPost(idPost) {
    try {
      await removerCurtida(idPost);
      setResponseRemover(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { responseRemover, removerCurtidaPost, setResponseRemover };
}
