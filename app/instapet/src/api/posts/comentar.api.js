//localhost:8080/comentarios/comentar/1

import { axiosInstance } from '../_base/axios-instance';

export async function comentarPost(idPost, comentario) {
  const response = await axiosInstance.post(
    `/comentarios/comentar/` + idPost,
    {
      comentario: comentario,
    },
    {}
  );
  return response.data;
}
