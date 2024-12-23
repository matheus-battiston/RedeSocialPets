import { Avatar } from "@mui/material"
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import "./index.css"

export function CardAmigo({amigo, removerAmigo}){

    function removeAmigo(){
        removerAmigo(amigo.id)
    }


    return (
        <div className="estiloCardAmigo">
            <Avatar src={amigo.urlFotoPerfil} sx={{ width: 100, height: 100 }}/>
            <p className="nomeAmigo">{amigo.nome}</p>

            <IconButton onClick={removeAmigo}>
                <DeleteIcon sx={{ width: 50, height: 50 }}/>
            </IconButton>
        </div>
    )
}