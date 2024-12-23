import './index.css'
import {Avatar, Button} from "@mui/material";
import {logout} from "../../../api/user/logout.api";
import useGlobalUser from "../../../context/user.context";
import {useGetMe} from "../../../hook/useGetMe/useGetMe.hook";
import {InfoUsuarioMenu, PedidosAmizadeRecebidos} from "../index";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { useNavigate } from 'react-router-dom';


export function Menu({postar}){

    const [,setUser] = useGlobalUser()
    const {me, getUsuarioLogado} = useGetMe()
    const navigate = useNavigate()

    function logoutHandler(){
        logout()
        setUser(null)
    }

    function irParaAmigos(){
        navigate("/amigos")
    }

    function procurarUsuarios(){
        navigate("/procurar")
    }

    return (
        <div className="menuPrincipal">
            {me? <InfoUsuarioMenu usuario={me} /> : null}
            <section className="funcionalidadesMenu">
                <List>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Postar" onClick={postar}/>
                        </ListItemButton>
                    </ListItem>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Amigos" onClick={irParaAmigos}/>
                        </ListItemButton>
                    </ListItem>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Procurar" onClick={procurarUsuarios}/>
                        </ListItemButton>
                    </ListItem>
                </List>
            </section>

            <section>
                <PedidosAmizadeRecebidos />
            </section>
            <section className="botaoLogout">
                <Button onClick={logoutHandler} color="secondary" variant="contained">
                    Logout
                </Button>
            </section>
        </div>
    )
}