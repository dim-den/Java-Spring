import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';

class FilmCastEdit extends Component {

    emptyItem = {
        roletType:'',
        roleName:'',
        actorId:0,
        filmId:0
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
            const filmCast = await (await fetch(`/api/filmCast/${this.props.match.params.id}`)).json();
            this.setState({ item: filmCast });
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

        await fetch('/api/filmCast' + (item.id ? '/update/' + item.id : '/save'), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/filmCasts');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit film cast' : 'Add film cast'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="roleType">Role type</Label>
                        <Input type="text" name="roleType" id="roleType" value={item.roleType || ''}
                               onChange={this.handleChange} autoComplete="roleType"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="roleName">Role name</Label>
                        <Input type="text" name="roleName" id="roleName" value={item.roleName || ''}
                               onChange={this.handleChange} autoComplete="roleName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="actorId">Actor ID</Label>
                        <Input type="number" name="actorId" id="actorId" value={item.actorId || ''}
                               onChange={this.handleChange} autoComplete="actorId"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="filmId">Film ID</Label>
                        <Input type="number" name="filmId" id="filmId" value={item.filmId || ''}
                               onChange={this.handleChange} autoComplete="filmId"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/filmCasts">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(FilmCastEdit);