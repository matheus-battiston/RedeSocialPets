import { axiosInstance } from '../_base/axios-instance';

export async function removerCurtida(idPost) {
  const response = await axiosInstance.post(
    `/posts/remover-curtida/` + idPost,
    {},
    {}
  );
  return response.data;
}
