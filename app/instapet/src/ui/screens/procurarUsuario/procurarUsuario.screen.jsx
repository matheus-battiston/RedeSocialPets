import { useEffect, useState } from "react"
import { useBuscarUsuario } from "../../../hook/useBuscarUsuarios/useBuscarUsuarios.hook"
import { useFazerPedido } from "../../../hook/usePedidoAmizade/usePedidoAmizade.hook"
import { Background } from "../../components"
import { CardUsuario } from "../../components/cardUsuario/cardUsuario.component"
import "./index.css"

export function ProcurarUsuario(){

    const { responseBuscar, buscarUsuariosFunc } = useBuscarUsuario()
    const { response, fazerPedido, setResponse } = useFazerPedido()
    const [pesquisa, setPesquisa] = useState('')

    function mudancaTexto(event){
        const {value} = event.target
        setPesquisa(value)
    }

    function pesquisar(){
        buscarUsuariosFunc(pesquisa)
    }

    function adicionarAmigo(idAmigo){
        fazerPedido(idAmigo)
    }

    useEffect(() => {
        if (responseBuscar === true){
            buscarUsuariosFunc('')
            setResponse(false)
        }
    }, [responseBuscar])

    useEffect( () => {
        if (response === true){
            buscarUsuariosFunc('')
            setResponse(false)
        }
    }, [response])

    return(
        <Background>
            <div>
                <input type="text" onChange={mudancaTexto}/>
                <button onClick={pesquisar}>pesquisar</button>
                <div className='listaDeUsuarios'>
                {responseBuscar? responseBuscar.map(pessoa => {
                    return <CardUsuario amigo={pessoa} adicionarAmigo={adicionarAmigo}/>
                }) :null}
            </div>
            </div>
            
        </Background>
    )
}