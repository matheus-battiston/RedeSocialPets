import { axiosInstance } from '../_base/axios-instance';

export async function aceitarAmizade(idPedido) {
  const response = await axiosInstance.put(
    '/usuarios/aceitar-amizade/' + idPedido,
    {},
    {}
  );
  return response.data;
}
