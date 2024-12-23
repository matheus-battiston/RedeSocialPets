import { useEffect, useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { getPostsAmigos } from '../../api/posts/getPostsAmigos.api';

export function useGetPostsAmigos() {
  const [postsDosAmigos, setPostsDosAmigos] = useState();
  const [, setErro] = useGlobalErro();

  useEffect(() => {
    getPosts(0);
  }, []);

  async function getPosts(pagina) {
    try {
      const respostaApi = await getPostsAmigos(pagina);
      setPostsDosAmigos(respostaApi);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { postsDosAmigos, getPosts };
}
