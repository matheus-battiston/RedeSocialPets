import {useEffect, useState} from "react";
import {getPedidosAmizadesRecebidos} from "../../api/user/getPedidosAmizade.api";
import useGlobalErro from "../../context/erro/erro.context";

export function useGetPedidosPendentes(){
    const [pedidosPendentes, setPedidosPendentes] = useState()
    const [, setErro] = useGlobalErro();

    useEffect(() => {
        getPedidos()
    }, [])

    async function getPedidos(){
        try {
            const respostaApi = await getPedidosAmizadesRecebidos();
            setPedidosPendentes(respostaApi);
        } catch (error) {
            setErro(error.response.data.message || error.response.statusText);
        }
    }

    return {pedidosPendentes, getPedidos}
}