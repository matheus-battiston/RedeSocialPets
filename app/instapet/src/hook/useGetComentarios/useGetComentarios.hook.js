import { useEffect, useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { getComentariosPost } from '../../api/posts/getComentarios.api';

export function useGetComentarios(postId) {
  const [comentarios, setComentarios] = useState();
  const [, setErro] = useGlobalErro();

  useEffect(() => {
    getComentariosPostFunction(postId);
  }, []);

  async function getComentariosPostFunction(postId) {
    try {
      console.log('AQUI');
      const respostaApi = await getComentariosPost(postId);
      setComentarios(respostaApi);
    } catch (error) {
      console.log('ali');
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { comentarios, getComentariosPostFunction };
}
