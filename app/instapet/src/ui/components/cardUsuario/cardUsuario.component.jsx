import { Avatar } from "@mui/material"
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import "./index.css"

export function CardUsuario({amigo, adicionarAmigo}){

    function adicionaAmigo(){
        adicionarAmigo(amigo.id)
    }

    return (
        <div className="estiloCardAmigo">
            <Avatar src={amigo.urlFotoPerfil} sx={{ width: 100, height: 100 }}/>
            <p className="nomeAmigo">{amigo.nome}</p>

            <IconButton onClick={adicionaAmigo}>
                <AddIcon sx={{ width: 50, height: 50 }}/>
            </IconButton>
        </div>
    )
}