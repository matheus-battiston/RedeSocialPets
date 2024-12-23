import './index.css'
import { Button } from '@mui/material'
import SendIcon from '@mui/icons-material/Send';
import { useEffect, useState } from 'react';
import { usePostar } from '../../../hook/usePostar/usePostar.hook';

const PUBLICO = "PUBLICO"
const PRIVADO = "PRIVADO"

export function ModalPostar({fecharPost}){

    const { response, post, setResponse } = usePostar()

    const [inputForm, setInputForm] = useState({
        url: '',
        legenda: '',
        permissao: ''
    })

    function mudanca(event){
        const {name, value} = event.target
        setInputForm((oldFormInputs) => ({
            ...oldFormInputs,
            [name]: value,
          }));    
        }

    function enviar(event){
        event.preventDefault()
        post(inputForm)
    }

    function fecharModal(){
        fecharPost(false)
    }

    useEffect( () => {
        if (response === true){
            fecharPost(false)
            setResponse(false)
        }
    }, [response])

    return (
        <div className='backgroundModal'>
            <div className='content centralizar'>
                <button className='botaoFechar' onClick={fecharModal}>fechar</button>
                <form className='formComentario'>
                    <label className="label-form-post">URL</label>
                    <textarea className="estilo-input descricao" type="text" name="url" onChange={mudanca}/>

                    <label className="label-form-post">Legenda</label>
                    <textarea className="estilo-input descricao" type="text" name="legenda" onChange={mudanca}/>

                    <select className="espaco-select" onChange={mudanca} name="permissao" defaultValue="">
                        <option value="" disabled >Selecione a permissao</option>
                        <option value={PUBLICO}>Publico</option>
                        <option value={PRIVADO}>Privado</option>
                    </select>

                    <div className='botaoEnviarForm'> 
                        <Button onClick={enviar} color="secondary" variant="contained" endIcon={<SendIcon />}>
                            Enviar
                        </Button>
                    </div>
                </form>
            </div>
        </div>       
         )
}