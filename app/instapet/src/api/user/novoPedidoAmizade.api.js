import { axiosInstance } from '../_base/axios-instance';

export async function pedidoAmizade(idAmigo) {
  const response = await axiosInstance.post(
    `/usuarios/nova-amizade/` + idAmigo,
    {},
    {}
  );
  return response.data;
}
