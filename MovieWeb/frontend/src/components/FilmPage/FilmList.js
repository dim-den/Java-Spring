import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, getToken, makeTokenizedRequest } from './../../utils/Common';

class FilmList extends Component {

    constructor(props) {
        super(props);
        this.state = { films: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/films')
            .then(response => response.json())
            .then(data => { console.log(data); this.setState({ films: data })});
            
    }

    async remove(id) {
        await makeTokenizedRequest(`api/film/delete/${id}`, 'DELETE')
        .then(() => {
            let updatedfilms = [...this.state.films].filter(i => i.id !== id);
            this.setState({ films: updatedfilms });
        });
    }

    render() {
        const { films, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmList = films.map(film => {
            return <tr key={film.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{film.title}</td>
                <td>{film.genre}</td>
                <td>{film.description}</td>
                <td>{film.director}</td>
                <td>{film.country}</td>
                <td>{film.release.substring(0, 10)}</td>
                <td>{film.budget}</td>
                <td>{film.fees}</td>
                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"films/" + film.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(film.id)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                    : null
                }
            </tr>
        });

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/films/new">Add film</Button>
                        </div>
                        : null
                    }
                    <h3>Films</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="10%">Title</th>
                                <th width="10%">Genre</th>
                                <th width="25%">Description</th>
                                <th width="10%">Director</th>
                                <th width="10%">Country</th>
                                <th width="10%">Release date</th>
                                <th width="10%">Budget</th>
                                <th width="10%">Fees</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filmList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default FilmList;