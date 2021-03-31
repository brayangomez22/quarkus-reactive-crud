import axios from '../axios/axios'
import swal from 'sweetalert2'
import React, {useState, useEffect} from 'react'

export default function Table() {

    const [fruits, setFruits] = useState([])

    const getFruits = () => {
        axios.get('/fruits').then(response => {
            setFruits(response.data)
        })
    }

    useEffect(() => {
        getFruits()
    }, [])

    return (
        <div class="containerTable">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Amount</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {fruits.map(fruits => {
                        return <tr key={fruits.id}>
                                    <td>{fruits.id}</td>
                                    <td>{fruits.name}</td>
                                    <td>{fruits.amount}</td>
                                    <td><button type="button" className="buttonCreate buttonEdit">Edit</button></td>
                                    <td><button type="button" className="buttonCreate buttonDelete" onClick={() => {
                                        swal.fire({
                                            title: "Esta fruta será eliminada",
                                            text: "¿Estás seguro?",
                                            icon: "info",
                                            showCancelButton: true,
                                            cancelButtonText: "Cancelar",
                                            confirmButtonText: "¡Entendido!",
                                            confirmButtonColor: "#F8BF00",
                                            cancelButtonColor: "#FF0000",
                                        }).then(result => {
                                            if(result.isConfirmed ){
                                                axios.delete(`/fruits/${fruits.id}`).then(response => {
                                                    if (response.status === 204) {
                                                        swal.fire({
                                                            title: "¡Fruta elimindad con éxito!",
                                                            icon: "success",
                                                            confirmButtonText: "¡Entendido!",
                                                            confirmButtonColor: "#F8BF00",
                                                        }).then(result => {
                                                            if (result.isConfirmed) {
                                                                window.location.reload();
                                                            }
                                                        })
                                                    } else {
                                                        swal.fire({
                                                            title: "Error al eliminar la fruta",
                                                            footer: "Intente de nuevo",
                                                            icon: "error",
                                                            confirmButtonText: "¡Entendido!",
                                                            confirmButtonColor: "#F8BF00",
                                                        });
                                                    }
                                                })
                                            }
                                        })
                                    }}>Delete</button></td>
                                </tr>
                    })}
                </tbody>
            </table>
        </div> 
    )
}