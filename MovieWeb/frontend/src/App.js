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
import FilmGenreList from './components/FilmGenrePage/FilmGenreList';
import FilmGenreEdit from "./components/FilmGenrePage/FilmGenreEdit";
import GenreList from './components/GenrePage/GenreList';
import GenreEdit from "./components/GenrePage/GenreEdit";
import FilmCastList from './components/FilmCastPage/FilmCastList';
import FilmCastEdit from "./components/FilmCastPage/FilmCastEdit";
import Login from "./components/LoginPage/Login";
import Register from "./components/RegisterPage/Register";
import PrivateRoute from './utils/PrivateRoute';

class App extends Component {
  componentDidMount(){
    document.title = "Movie App"
  }

  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            
            <PrivateRoute hasRole="USER" path='/films' exact={true} component={FilmList}/>
            <PrivateRoute hasRole="ADMIN" path='/films/:id' component={FilmEdit}/>
            <PrivateRoute hasRole="USER" path='/actors' exact={true} component={ActorList}/>
            <PrivateRoute hasRole="ADMIN" path='/actors/:id' component={ActorEdit}/>
            <PrivateRoute hasRole="ADMIN" path='/users' exact={true} component={UserList}/>
            <PrivateRoute hasRole="ADMIN" path='/users/:id' component={UserEdit}/>
            <PrivateRoute hasRole="USER" path='/filmReviews' exact={true} component={FilmReviewList}/>
            <PrivateRoute hasRole="ADMIN" path='/filmReviews/:id' component={FilmReviewEdit}/>
            <PrivateRoute hasRole="USER" path='/filmCasts' exact={true} component={FilmCastList}/>
            <PrivateRoute hasRole="ADMIN" path='/filmCasts/:id' component={FilmCastEdit}/>
            <PrivateRoute hasRole="USER" path='/filmGenres' exact={true} component={FilmGenreList}/>
            <PrivateRoute hasRole="ADMIN" path='/filmGenres/:id' component={FilmGenreEdit}/>
            <PrivateRoute hasRole="USER" path='/genres' exact={true} component={GenreList}/>
            <PrivateRoute hasRole="ADMIN" path='/genres/:id' component={GenreEdit}/>
            <Route path='/login' component={Login}/>
            <Route path='/register' component={Register}/>
          </Switch>
        </Router>
    )
  }
}

export default App;