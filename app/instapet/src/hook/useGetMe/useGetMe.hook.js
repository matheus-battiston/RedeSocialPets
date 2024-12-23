import {useEffect, useState} from "react";
import {getMe} from "../../api/user/getMe.api";
import useGlobalErro from "../../context/erro/erro.context";

export function useGetMe(){
    const [me, setMe] = useState()
    const [, setErro] = useGlobalErro();

    useEffect(() => {
        getUsuarioLogado()
    }, [])

    async function getUsuarioLogado(){
        try {
            const respostaApi = await getMe();
            setMe(respostaApi);
        } catch (error) {
            setErro(error.response.data.message || error.response.statusText);
        }
    }
    return {me, getUsuarioLogado}
}