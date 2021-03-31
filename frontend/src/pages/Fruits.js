import React, { Component } from 'react'
import { Link } from 'react-router-dom'

import Table from '../components/Table'
import FormFruits from '../components/FormFruits'

export default class Fruits extends Component {
    render() {
        return (
            <div class="container containerFruits">
                <div className="overlay">
                    <div class="navbar">
                        <div class="menu">
                            <Link to="/" className="linkHome"><h3 class="logo">Ho<span>me</span>  /  Fruit List</h3></Link>
                        </div>
                    </div>

                    <FormFruits />
                    <Table />
                </div>

            </div>
        )
    }
}
