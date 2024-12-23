import { axiosInstance } from '../_base/axios-instance';

export async function recusarAmizade(idPedido) {
  const response = await axiosInstance.put(
    '/usuarios/recusar-amizade/' + idPedido,
    {},
    {}
  );
  return response.data;
}
