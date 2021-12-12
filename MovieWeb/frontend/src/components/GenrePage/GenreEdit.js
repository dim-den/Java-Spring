import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { getToken, makeTokenizedRequest } from './../../utils/Common';

class GenreEdit extends Component {

    emptyItem = {
        name:''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            error: null
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const genre = await (await makeTokenizedRequest(`/api/genre/${this.props.match.params.id}`)).data;
            this.setState({ item: genre });
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

        await makeTokenizedRequest('/api/genre' + (item.id ? '/update/' + item.id : '/save'), 
                                   (item.id) ? 'PUT' : 'POST',
                                    JSON.stringify(item))
                                    .then(response =>  this.props.history.push('/genres'))
                                    .catch(error => {
                                        if (error.response.status === 400) this.setState({ error: error.response.data.errors[0], loading: false}); 
                                        else this.setState({ error: "Wrong value", loading: false});
                                    });
    }

    render() {
        const {item} = this.state;
        const { error } = this.state;
        const title = <h2>{item.id ? 'Edit genre' : 'Add genre'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <h4 style={{ color: 'red' }}>{error}</h4>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/genres">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(GenreEdit);