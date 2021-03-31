import React from 'react';
import { Link } from 'react-router-dom';

import './styles/style.css'

class Badge extends React.Component {
  render() {

    const transition = () => {
        const container = document.querySelector(".container");
        container.classList.toggle("active");
    }

    return (
        <div class="container">
            <div class="navbar">
                <div class="menu">
                    <h3 class="logo">Fruits<span>Sofka</span></h3>
                    <div class="hamburger-menu" onClick={transition}>
                        <div class="bar"></div>
                    </div>
                </div>
            </div>

            <div class="main-container">
                <div class="main">
                    <header>
                        <div class="overlay">
                            <div class="inner">
                               <div className="titleHome">
                                    <h2 class="title">Future is here</h2>
                                    <p>
                                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                                    Laudantium illum tenetur consequatur veritatis?
                                    </p>
                               </div>
                                <Link to="fruits" class="btn btnStart">Start</Link>
                            </div>
                        </div>
                    </header>
                </div>

                <div class="shadow one"></div>
                <div class="shadow two"></div>
            </div>

            <div class="links">
                <ul>
                    <li>
                        <a href="#">Home</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Portfolio</a>
                    </li>
                </ul>
            </div>
        </div>
    );
  }
}

export default Badge;
