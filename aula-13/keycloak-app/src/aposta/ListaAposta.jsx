import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";


function ListaAposta() {

    
    const { keycloak, initialized } = useKeycloak();
    const [data, setData] = useState([]);

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
          fetch('http://localhost:8081/aposta', {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${keycloak.token}`, // Adiciona o token ao cabeçalho
            },
          }).then(response => {
            return response.json()
          }).then(data => {
            setData(data)
          })
        }
      }, [initialized, keycloak]);


    return (
        <div>

            <table>
                <tr>
                <td>Id</td>
                <td>Id Partida</td>
                <td>Data da Aposta</td>
                <td>Resultado</td>
                <td>Valor</td>
                <td>Status</td>
                </tr>
                {data.map((aposta, index) => {

                return <tr>
                    <td>{aposta.id}</td>
                    <td>{aposta.idPartida}</td>
                    <td>{aposta.dataAposta}</td>
                    <td>{aposta.resultado}</td>
                    <td>{aposta.valor}</td>
                    <td>{aposta.status}</td>
                </tr>

                })}

            </table>

        </div>
    )

}

export default ListaAposta;