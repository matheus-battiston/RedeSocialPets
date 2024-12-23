import { axiosInstance } from '../_base/axios-instance';

export async function getPedidosAmizadesRecebidos() {
  const response = await axiosInstance.get(
    '/usuarios/pedidos-amizade-pendente',
    {},
    {}
  );
  return response.data;
}
