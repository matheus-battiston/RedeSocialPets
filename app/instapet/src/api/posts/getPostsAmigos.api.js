import { axiosInstance } from '../_base/axios-instance';

export async function getPostsAmigos(pagina) {
  const response = await axiosInstance.get(
    `/posts/listar-posts-home?size=2&page=${pagina}&sort=horario,desc`,
    {},
    {}
  );
  return response.data;
}
