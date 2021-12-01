import React, { Component } from 'react';
import './App.css';
import Home from './components/HomePage/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import FilmList from './components/FilmPage/FilmList';
import FilmEdit from "./components/FilmPage/FilmEdit";
import ActorList from './components/ActorPage/ActorList';
import ActorEdit from "./components/ActorPage/ActorEdit";
import UserList from './components/UserPage/UserList';
import UserEdit from "./components/UserPage/UserEdit";
import FilmReviewList from './components/FilmReviewPage/FilmReviewList';
import FilmReviewEdit from "./components/FilmReviewPage/FilmReviewEdit";
import FilmCastList from './components/FilmCastPage/FilmCastList';
import FilmCastEdit from "./components/FilmCastPage/FilmCastEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/films' exact={true} component={FilmList}/>
            <Route path='/films/:id' component={FilmEdit}/>
            <Route path='/actors' exact={true} component={ActorList}/>
            <Route path='/actors/:id' component={ActorEdit}/>
            <Route path='/users' exact={true} component={UserList}/>
            <Route path='/users/:id' component={UserEdit}/>
            <Route path='/filmReviews' exact={true} component={FilmReviewList}/>
            <Route path='/filmReviews/:id' component={FilmReviewEdit}/>
            <Route path='/filmCasts' exact={true} component={FilmCastList}/>
            <Route path='/filmCasts/:id' component={FilmCastEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;