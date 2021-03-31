import React from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Home from '../pages/Home';
import Fruits from '../pages/Fruits'
import NotFound from '../pages/NotFound';

function App() {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={Home} />
                <Route exact path="/fruits" component={Fruits} />
                <Route component={NotFound} />
            </Switch>
        </BrowserRouter>
    );
}

export default App