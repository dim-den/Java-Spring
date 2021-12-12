import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';

class FilmGenreList extends Component {

    constructor(props) {
        super(props);
        this.state = { filmGenres: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/filmGenres')
        .then(response => this.setState({ filmGenres: response.data }));
    }

    async remove(id) {
        await makeTokenizedRequest(`api/filmGenre/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedFilmGenres = [...this.state.filmGenres].filter(i => i.id !== id);
                this.setState({ filmGenres: updatedFilmGenres });
            });
    }

    render() {
        const { filmGenres, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmGenreList = filmGenres.map(filmGenre => {
            return <tr key={filmGenre.id}>
                <td>{filmGenre.id}</td>
                <td>{filmGenre.filmId}</td>
                <td>{filmGenre.genreId}</td>

                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"filmGenres/" + filmGenre.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(filmGenre.id)}>Delete</Button>
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
                            <Button color="success" tag={Link} to="/filmGenres/new">Add film genre</Button>
                        </div>
                        : null
                    }
                    <h3>Film genres</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>    
                                <th width="40%">Film ID</th>
                                <th width="40%">Genre ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filmGenreList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default FilmGenreList;