import './index.css'
import { Button } from '@mui/material'
import SendIcon from '@mui/icons-material/Send';
import { useEffect, useState } from 'react';
import { useComentarPost } from '../../../hook/useComentarPost/useComentarPost.hook';


export function ModalComentario({postId, fechar}){

    const [comentario, setComentario] = useState()
    const { response, comentar, setResponse } = useComentarPost()

    function mudanca(event){
        const {value} = event.target
        setComentario(value)
    }

    useEffect(() => {
        console.log(comentario)
    },[comentario])

    function fecharModal(){
        fechar(false)
    }

    function enviar(){
        comentar(postId, comentario)
    }

    useEffect(() => {
        if (response === true){
            setResponse(false)
            fechar(false)
        }
    }, [response])

    return (
        <div className='backgroundModal'>
            <div className='content centralizar'>
                <button className='botaoFechar' onClick={fecharModal}>fechar</button>
                <form className='formComentario'>
                    <label className="label-form">Descrição</label>
                    <textarea className="estilo-input descricao" type="text" name="descricao" onChange={mudanca}/>
                    <div className='botaoEnviarForm'> 
                        <Button onClick={enviar} color="secondary" variant="contained" endIcon={<SendIcon />}>
                            Comentar
                        </Button>
                    </div>
                </form>
            </div>
        </div>    
    )
}