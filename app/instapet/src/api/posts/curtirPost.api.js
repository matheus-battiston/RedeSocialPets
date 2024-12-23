import { axiosInstance } from '../_base/axios-instance';

export async function curtirPost(idPost) {
  const response = await axiosInstance.post(`/posts/curtir/` + idPost, {}, {});
  return response.data;
}
