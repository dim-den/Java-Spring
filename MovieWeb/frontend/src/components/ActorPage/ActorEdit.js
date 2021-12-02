import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { getToken, makeTokenizedRequest } from './../../utils/Common';

class ActorEdit extends Component {

    emptyItem = {
        name: '',
        surname: '',
        country: '',
        bday: null
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
            const actor = await (await makeTokenizedRequest(`/api/actor/${this.props.match.params.id}`)).json();
            this.setState({ item: actor });
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

        await makeTokenizedRequest('/api/actor' + (item.id ? '/update/' + item.id : '/save'), 
        (item.id) ? 'PUT' : 'POST',
         JSON.stringify(item));

        this.props.history.push('/actors');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit actor' : 'Add actor'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="name" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="surname">Surname</Label>
                        <Input type="text" name="surname" id="surname" value={item.surname || ''}
                               onChange={this.handleChange} autoComplete="surname"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="country">Country</Label>
                        <Input type="text" name="country" id="country" value={item.country || ''}
                               onChange={this.handleChange} autoComplete="country"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="bday">Birthday</Label>
                        <Input type="date" name="bday" id="bday" value={item.bday || ''}
                               onChange={this.handleChange} autoComplete="bday"/>
                    </FormGroup>
             
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/actors">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(ActorEdit);