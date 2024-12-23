import { useGetPostsAmigos } from '../../../hook/useGetPosts/useGetPosts.hook'
import './index.css'
import { ContainerPost } from '../containerPost/containerPost.component'
import { useEffect, useState } from 'react'
import { BotaoTexto } from '../botaoTexto/botaoTexto.component'

export function Posts(){
    const {postsDosAmigos, getPosts} = useGetPostsAmigos()
    const [pagina, setPagina] = useState(0)
    const [desabilitarProx, setDesabilitarProx] = useState(false)
    const [desabilitarAnterior, setDesabilitarAnterior] = useState(false)


    useEffect(() => {
        console.log(pagina)
        getPosts(pagina)
        if(postsDosAmigos?.totalPages === pagina + 1){
            setDesabilitarProx(true)
        } else {
            setDesabilitarProx(false)
        }

        if (pagina === 0){
            setDesabilitarAnterior(true)
        } else{
            setDesabilitarAnterior(false)
        }
    }, [pagina])


    function clickPagina(event){
        const {value} = event.target
        const novaPagina = pagina + parseInt(value)
        setPagina(novaPagina)
    }

    
    function atualizar(){
        getPosts(pagina)
    }

    useEffect(() => {
        console.log(postsDosAmigos)
    }, [postsDosAmigos])

    return (
        <section className="listaDePosts centralizar">
            {postsDosAmigos?.content[0]? <ContainerPost atualizar={atualizar} postsDosAmigos={postsDosAmigos.content[0]} /> : null}
            {postsDosAmigos?.content[1]? <ContainerPost atualizar={atualizar} postsDosAmigos={postsDosAmigos.content[1]} /> : null}
            <section className='seletorPagina'>
                <BotaoTexto onClick={clickPagina} valor={-1} texto="Pagina anterior" classeAdicional="maior" disabled={desabilitarAnterior}/>
                <BotaoTexto onClick={clickPagina} valor={+1} texto="Proxima pagina" classeAdicional="maior" disabled={desabilitarProx}/>
            </section>
        </section>
    )
}