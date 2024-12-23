import './index.css'
import {TextField, Button} from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import {useState, useEffect} from "react";
import {login} from "../../../api/user/login.api";
import useGlobalUser from "../../../context/user.context";
import {useNavigate} from "react-router-dom";

export function FormLogin(){

    const [error, setError] = useState(null)
    const [formInput, setFormInput] = useState({username: '', password: ''})
    const [user,setUser] = useGlobalUser()
    const navigate = useNavigate()


    function mudancaInput(event){
        const {name, value} = event.target
        setFormInput(oldFormInput => ({...oldFormInput, [name]: value}))
    }

    async function logar(event) {
        event.preventDefault()
        try {
            const user = await login({ username: formInput.username, password: formInput.password })
            setUser({user})
        } catch(error){
            setError("Usuario ou senha invalido")
        }
    }

    useEffect( () => {
        if (user){
            navigate("/home")
        }
    }, [user])

    return (
        <form className="formStyle centralizar" onSubmit={logar}>
            <TextField id="username" label="Usuario" variant="outlined" onChange={mudancaInput} name="username"/>
            <TextField id="username" label="Senha" variant="outlined" type="password" margin="dense" onChange={mudancaInput} name="password"/>
            {error? <p className="error">{error}</p> : null}
            <section className="botao">
                <Button onClick={logar} color="secondary" variant="contained" endIcon={<SendIcon />}>
                    Login
                </Button>
            </section>

        </form>
    )
}