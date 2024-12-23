import { axiosInstance } from '../_base/axios-instance';

export async function logout() {
    const response = await axiosInstance.post('/logout', {}, {});
    return response.data;
}
