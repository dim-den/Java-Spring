import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';

class GenreList extends Component {

    constructor(props) {
        super(props);
        this.state = { genres: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/genres')
            .then(response => this.setState({ genres: response.data }));
    }

    async remove(id) {
        await makeTokenizedRequest(`api/genre/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedGenres = [...this.state.genres].filter(i => i.id !== id);
                this.setState({ genres: updatedGenres });
            });
    }

    render() {
        const { genres, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const genreList = genres.map(genre => {
            return <tr key={genre.id}>
                <td>{genre.id}</td>
                <td>{genre.name}</td>

                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"genres/" + genre.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(genre.id)}>Delete</Button>
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
                            <Button color="success" tag={Link} to="/genres/new">Add genre</Button>
                        </div>
                        : null
                    }
                    <h3>Genres</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>                           
                                <th width="65%">Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            {genreList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default GenreList;