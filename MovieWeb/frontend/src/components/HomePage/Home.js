import React, { Component } from 'react';
import { userAuthrorized } from './../../utils/Common';
import AppNavbar from './../Navbar/AppNavbar';
import SearchBar from "./../SearchBar/SearchBar";
import './../../App.css';
import { withRouter } from 'react-router-dom';

class Home extends Component {
    constructor(props) {
        super(props);
    };

    render() {
        return (
            <div>
                <AppNavbar />
                {userAuthrorized() ?
                    <div className="home">
                        <SearchBar placeholder="Looking for..." />
                    </div>
                    :
                    <h4 className='mt-5'>You have to authorize first</h4>
                }
            </div>
        );
    }
}
export default withRouter(Home);