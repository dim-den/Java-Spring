import React, { Component } from 'react';
import './../../App.css';
import AppNavbar from './../Navbar/AppNavbar';
import { withRouter } from 'react-router-dom';
import { getEmail } from './../../utils/Common';

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: getEmail()
        }
    }

    render() {
        const { user } = this.state;

        return (
            <div>
                <AppNavbar />
                <div>
                    Welcome {user ? user : 'new user'}!<br /><br />
                </div>
            </div>
        );
    }
}
export default withRouter(Home);