import { useEffect, useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { getAmigos } from '../../api/user/getAmigos.api';
export function useGetAmigos() {
  const [response, setResponse] = useState([]);
  const [, setErro] = useGlobalErro();

  useEffect(() => {
    acharAmigos('');
  }, []);

  async function acharAmigos(filtro) {
    try {
      const respostaApi = await getAmigos(filtro);
      setResponse(respostaApi);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { response, acharAmigos };
}
