import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import FilmList from './FilmList';
import FilmEdit from "./FilmEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/films' exact={true} component={FilmList}/>
            <Route path='/films/:id' component={FilmEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;