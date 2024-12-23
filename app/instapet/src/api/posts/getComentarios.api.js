import { axiosInstance } from '../_base/axios-instance';

export async function getComentariosPost(idPost) {
  const response = await axiosInstance.get(
    `/comentarios/listar/` + idPost,
    {},
    {}
  );
  return response.data;
}
