import { Avatar } from '@mui/material'
import { useEffect } from 'react'
import { useGetComentarios } from '../../../hook/useGetComentarios/useGetComentarios.hook'
import { Comentario } from '../comentario/comentario.component'
import './index.css'

export function Modal({fechar, postId}){

    const { comentarios } = useGetComentarios(postId)

    function fecharModal(){
        fechar(false)
    }

    useEffect(() => {
        console.log(comentarios)
    }, [comentarios])

    return (
        <div className='backgroundModal'>
            <div className='content centralizar'>
                <button className='botaoFechar' onClick={fecharModal}>fechar</button>
                <section className='comentarios'>
                    {comentarios? comentarios.map(comentario => {
                        return (
                            <Comentario comentario={comentario} />
                        )
                    }) : null}
                </section>
            </div>
        </div>
    )
}