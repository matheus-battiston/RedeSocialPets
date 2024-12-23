import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { comentarPost } from '../../api/posts/comentar.api';
export function useComentarPost() {
  const [response, setResponse] = useState(false);
  const [, setErro] = useGlobalErro();

  async function comentar(idPost, comentario) {
    try {
      await comentarPost(idPost, comentario);
      setResponse(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { response, comentar, setResponse };
}
