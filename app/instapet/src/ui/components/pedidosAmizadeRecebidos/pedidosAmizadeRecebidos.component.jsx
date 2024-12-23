import './index.css'
import {useGetPedidosPendentes} from "../../../hook/useGetPedidosPendentes/useGetPedidosPendetes.hook";
import {Avatar} from "@mui/material";
import { useAceitarAmizade } from '../../../hook/useAceitarAmizade/useAceitarAmizade.hook';
import { useEffect } from 'react';
import { BotaoTexto } from '../botaoTexto/botaoTexto.component';
import { useRecusarAmizade } from '../../../hook/useRecusarAmizade/useRecusarAmizade.hook';

const ACEITAR = "aceitar"
const RECUSAR = "recusar"

export function PedidosAmizadeRecebidos(){
    const {pedidosPendentes, getPedidos} = useGetPedidosPendentes()
    const { aceitar, sucesso } = useAceitarAmizade()
    const {recusar, sucessoRecusar} = useRecusarAmizade()

    async function onClick(event){
        const {name, value} = event.target
        if (name === ACEITAR){
            aceitar(value)
        } else if (name == RECUSAR) {
            recusar(value)
        }
    }

    useEffect(() => {
        if (sucesso === true){
            getPedidos()
        }
    }, [sucesso])


    useEffect(() => {
        if (sucessoRecusar === true){
            getPedidos()
        }
    }, [sucessoRecusar])

    return (
        <section className="pedidosAmizade">
            {pedidosPendentes?.map(pedido => {
                return (
                    <div className="containerPedidoAmizade">
                        <Avatar sx={{ width: 50, height: 50 }} alt="Remy Sharp" src={pedido.requerente.urlFotoPerfil} />
                        <div className="nomeAcao">
                            <p>{pedido.requerente.nome}</p>
                            <section className='acoes'>
                                <BotaoTexto onClick={onClick} valor={pedido.idPedido} nome={ACEITAR} texto="Aceitar"/>
                                <BotaoTexto onClick={onClick} valor={pedido.idPedido} nome={RECUSAR} texto="Recusar"/>
                            </section>
                        </div>
                    </div>
                )
            })}
        </section>
    )
}