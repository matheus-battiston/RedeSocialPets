import './index.css'
import {Background} from "../../components";
import { useGetAmigos } from '../../../hook/useGetAmigos/useGetAmigos.hook';
import { useEffect, useState } from 'react';
import { CardAmigo } from '../../components/cardAmigo/cardAmigo.component';
import { useRemoverAmigo } from '../../../hook/useRemoverAmigo/useRemoverAmigo';

export function Amigo(){
    const { response, acharAmigos, setResponse } = useGetAmigos()
    const  { responseRemover, removerAmigoFunc, setResponseRemover } = useRemoverAmigo()
    const [pesquisa, setPesquisa] = useState('')

    function removerAmigo(idAmigo){
        removerAmigoFunc(idAmigo)
    }

    function mudancaTexto(event){
        const {value} = event.target
        setPesquisa(value)
    }

    function pesquisar(){
        acharAmigos(pesquisa)
    }
    

    useEffect(() => {
        if (responseRemover === true){
            acharAmigos('')
            setResponseRemover(false)
        }
    }, [responseRemover])

    return (
        <Background>
            <div>
                <input type="text" onChange={mudancaTexto}/>
                <button onClick={pesquisar}>pesquisar</button>
                <div className='listaDeAmigos'>
                    {response? response.map(pessoa => {
                        return <CardAmigo amigo={pessoa} removerAmigo={removerAmigo}/>
                    }) :null}
                </div>
            </div>
           

        </Background>
    )
}