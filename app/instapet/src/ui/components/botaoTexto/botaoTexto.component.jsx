import './index.css'

export function BotaoTexto({onClick, valor, nome, texto, classeAdicional, disabled}){
    return (
        <button disabled={disabled} onClick={onClick} value={valor} name={nome} className={`botaoAceitarRecusar ${classeAdicional}`} >{texto}</button>
    )
}