//localhost:8080/comentarios/comentar/1

import { axiosInstance } from '../_base/axios-instance';

export async function postar({ urlFoto, legenda, permissao }) {
  const response = await axiosInstance.post(
    `/posts/postar/`,
    {
      urlFoto: urlFoto,
      legenda: legenda,
      permissao: permissao,
    },
    {}
  );
  return response.data;
}
