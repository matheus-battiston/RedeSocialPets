//localhost:8080/usuarios/listarAmigos?texto=cwi
import { axiosInstance } from '../_base/axios-instance';

export async function getAmigos(filtro) {
  const response = await axiosInstance.get(
    'usuarios/listar-amigos?texto=' + filtro,
    {},
    {}
  );
  return response.data;
}
