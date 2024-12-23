//localhost:8080/usuarios/listarAmigos?texto=cwi
import { axiosInstance } from '../_base/axios-instance';

export async function removerAmigo(idAmigo) {
  const response = await axiosInstance.put(
    'usuarios/desfazer-amizade/' + idAmigo,
    {},
    {}
  );
  return response.data;
}
