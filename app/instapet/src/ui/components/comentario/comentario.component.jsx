import { Avatar } from "@mui/material"
import "./index.css"

export function Comentario({comentario}){
    return (
        <section className="comentarioEstilo">
            <div className="nomeFoto">
                <Avatar src={comentario.usuario.urlFotoPerfil} />
                <p>{comentario.usuario.nome}</p>
            </div>
            <p className="textoComentario">{comentario.comentario}</p>
        </section>
    )
}