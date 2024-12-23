import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { postar } from '../../api/posts/postar.api';

export function usePostar() {
  const [response, setResponse] = useState(false);
  const [, setErro] = useGlobalErro();

  async function post(post) {
    try {
      await postar({
        urlFoto: post.url,
        legenda: post.legenda,
        permissao: post.permissao,
      });
      setResponse(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { response, post, setResponse };
}
