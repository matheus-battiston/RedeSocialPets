import './index.css'
import {Avatar} from "@mui/material";

export function InfoUsuarioMenu({usuario}){
    return (
        <section className="infoUsuarioMenu">
            <Avatar sx={{ width: 100, height: 100 }} alt="Remy Sharp" src={usuario.urlFotoPerfil} />
            <p className="nomePessoaMenu">{usuario.nome}</p>
        </section>
    )
}