import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import {  makeTokenizedRequest } from './../../utils/Common';

class UserEdit extends Component {

    emptyItem = {
        email: '',
        username: '',
        passwordHash: '',
        role: '',
        status: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const user = await (await makeTokenizedRequest(`/api/user/${this.props.match.params.id}`)).json();
            this.setState({ item: user });
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = { ...this.state.item };
        item[name] = value;
        this.setState({ item });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const { item } = this.state;

        await makeTokenizedRequest('/api/user' + (item.id ? '/update/' + item.id : '/save'),
            (item.id) ? 'PUT' : 'POST',
            JSON.stringify(item));

        this.props.history.push('/users');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit user' : 'Add user'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="email" name="email" id="email" value={item.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="username">Username</Label>
                        <Input type="text" name="username" id="username" value={item.username || ''}
                               onChange={this.handleChange} autoComplete="username"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="text">Password</Label>
                        <Input type="text" name="passwordHash" id="passwordHash" value={item.passwordHash || ''}
                               onChange={this.handleChange} autoComplete="passwordHash"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="role">Role</Label>
                        <Input type="text" name="role" id="role" value={item.role || ''}
                               onChange={this.handleChange} autoComplete="role"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="status">Status</Label>
                        <Input type="text" name="status" id="status" value={item.status || ''}
                               onChange={this.handleChange} autoComplete="status"/>
                    </FormGroup>
             
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/users">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(UserEdit);