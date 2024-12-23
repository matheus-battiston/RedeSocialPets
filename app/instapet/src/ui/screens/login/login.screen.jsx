import "./index.css"
import {Background, ContainerLogin, FormLogin} from "../../components";


export function Login(){
    return (
        <Background>
            <ContainerLogin>
                <FormLogin />
            </ContainerLogin>
        </Background>
    )
}