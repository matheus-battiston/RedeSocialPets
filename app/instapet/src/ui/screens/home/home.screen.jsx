import './index.css'
import useGlobalUser from "../../../context/user.context";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {Background, Menu, ModalPostar, Posts} from "../../components";

export function Home(){

    const [modalPostar, setModalPostar] = useState(false)
    const [user, setUser] = useGlobalUser()
    const navigate = useNavigate()

    useEffect( () => {
        if (!user) {
            navigate("/")
        }
    }, [user])

    function postar(){
        setModalPostar(true)
    }


    return (
        <Background>
            {modalPostar === true? <ModalPostar fecharPost={setModalPostar}/> : null}
            <Menu postar={postar} />
            <Posts />
        </Background>
    )
}