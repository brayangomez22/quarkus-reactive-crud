import React from 'react'
import axios from '../axios/axios'
import {useForm} from 'react-hook-form'

export default function FormFruits() {

    const {register, handleSubmit} = useForm();

    const onSubmit = data => {
        axios.post("/fruits", data).then(
            response => {
                if (response.status === 201){
                    window.location.reload();
                }
            }
        );
    }

    return (
        <form className="containerInputs" onSubmit={handleSubmit(onSubmit)} >
            <div>
                <input type="text" placeholder="Name" name="name" ref={register} required/>
                <input type="number" name="number" required placeholder="Amount" name="amount" ref={register}/>
                <button type="submit" className="buttonCreate">Create</button>
            </div>
        </form>
    )
}
