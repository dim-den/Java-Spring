import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import axios from 'axios';
import { setUserSession } from './../../utils/Common';


class Register extends Component {

    emptyItem = {
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            error: null,
            loading: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = { ...this.state.item };
        item[name] = value;
        this.setState({ item: item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;

        this.setState({ error: null, loading: true })

        axios.post('/api/auth/register', item)
        .then(response => {
            setUserSession(response.data.token, response.data.email, response.data.role );
            this.props.history.push('/');
        }).catch(error => {
            if (error.response.status === 400) this.setState({ error: error.response.data.errors[0], loading: false});
            else if(error.response.status === 401) this.setState({ error: error.response.data, loading: false});
            else this.setState({ error: "Something went wrong. Please try again later.", loading: false});
        });
    }

    render() {
        const { item } = this.state;
        const title = <h2>Registration</h2>;
        const { error } = this.state;

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <h4 style={{ color: 'red' }}>{error}</h4>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="title">Email</Label>
                        <Input type="email" name="email" id="email" value={item.email || ''}
                            onChange={this.handleChange} autoComplete="email" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="title">Username</Label>
                        <Input type="text" name="username" id="username" value={item.username || ''}
                            onChange={this.handleChange} autoComplete="username" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="title">Password</Label>
                        <Input type="password" name="password" id="password" value={item.password || ''}
                            onChange={this.handleChange} autoComplete="password" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="title">Confirm password</Label>
                        <Input type="password" name="confirmPassword" id="confirmPassword" value={item.confirmPassword || ''}
                            onChange={this.handleChange} autoComplete="confirmPassword" />
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit" disabled={this.state.loading}>Register</Button>{' '}
                        <Button color="secondary" tag={Link} to="/login">Back to login</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(Register);