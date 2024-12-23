//localhost:8080/usuarios/listarAmigos?texto=cwi
import { axiosInstance } from '../_base/axios-instance';

export async function buscarUsuarios(filtro) {
  const response = await axiosInstance.get(
    'usuarios//buscar-usuarios?texto=' + filtro,
    {},
    {}
  );
  return response.data;
}
