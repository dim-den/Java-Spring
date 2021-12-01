import React, { Component } from 'react';
import './../../App.css';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/users">Users</Link></Button>
                    <Button color="link"><Link to="/films">Films</Link></Button>
                    <Button color="link"><Link to="/actors">Actors</Link></Button>
                    <Button color="link"><Link to="/filmReviews">Film reviews</Link></Button>
                    <Button color="link"><Link to="/filmCasts">Film casts</Link></Button>
                </Container>
            </div>
        );
    }
}
export default Home;