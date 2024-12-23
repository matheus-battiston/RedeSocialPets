import { useEffect, useState } from 'react';
import useGlobalErro from '../../context/erro/erro.context';
import { buscarUsuarios } from '../../api/user/buscarUsuarios.api';
export function useBuscarUsuario() {
  const [responseBuscar, setResponseBuscar] = useState([]);
  const [, setErro] = useGlobalErro();

  useEffect(() => {
    buscarUsuariosFunc('');
  }, []);

  async function buscarUsuariosFunc(filtro) {
    try {
      const respostaApi = await buscarUsuarios(filtro);
      console.log(respostaApi);
      setResponseBuscar(respostaApi);
    } catch (error) {
      setErro(error.response.data.message || error.response.statusText);
    }
  }
  return { responseBuscar, buscarUsuariosFunc };
}
