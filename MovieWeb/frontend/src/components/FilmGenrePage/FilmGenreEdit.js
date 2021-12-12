import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { getToken, makeTokenizedRequest } from './../../utils/Common';

class FilmGenreEdit extends Component {

    emptyItem = {
        filmId:0,
        genreId:0
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
            const filmGenre = await (await makeTokenizedRequest(`/api/filmGenre/${this.props.match.params.id}`)).data;
            this.setState({ item: filmGenre });
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

        await makeTokenizedRequest('/api/filmGenre' + (item.id ? '/update/' + item.id : '/save'), 
                                   (item.id) ? 'PUT' : 'POST',
                                    JSON.stringify(item))
                                    .then(response =>  this.props.history.push('/filmGenres'))
                                    .catch(error => {
                                        if (error.response.status === 400) this.setState({ error: error.response.data.errors[0], loading: false}); 
                                        else this.setState({ error: "Wrong value", loading: false});
                                    });
    }

    render() {
        const {item} = this.state;
        const { error } = this.state;
        const title = <h2>{item.id ? 'Edit film genre' : 'Add film genre'}</h2>;
    
        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <h4 style={{ color: 'red' }}>{error}</h4>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="filmId">Film ID</Label>
                        <Input type="number" name="filmId" id="filmId" value={item.filmId || ''}
                               onChange={this.handleChange} autoComplete="filmId"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="genreId">Genre ID</Label>
                        <Input type="number" name="genreId" id="genreId" value={item.genreId || ''}
                               onChange={this.handleChange} autoComplete="genreId"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/filmGenres">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}
export default withRouter(FilmGenreEdit);