import { useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { curtirPost } from '../../api/posts/curtirPost.api';

export function useCurtirPost() {
  const [response, setResponse] = useState(false);
  const [, setErro] = useGlobalErro();

  async function curtir(idPost) {
    try {
      await curtirPost(idPost);
      setResponse(true);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { response, curtir, setResponse };
}
