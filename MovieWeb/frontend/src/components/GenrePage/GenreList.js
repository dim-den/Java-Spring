import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class GenreList extends Component {

    constructor(props) {
        super(props);
        this.state = { genres: [], currentGenres: [], totalGenres: 0, currentPage: null, totalPages: null, error: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/genres?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ genres: response.data }));

        makeTokenizedRequest(`/api/genres/count`)
            .then(response => this.setState({ totalGenres: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/genres?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentGenres = response.data;
                this.setState({ currentPage, currentGenres, totalPages });
            });
    }

    async remove(id) {
        this.setState({ error: null })

        await makeTokenizedRequest(`api/genre/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedGenres = [...this.state.genres].filter(i => i.id !== id);
                this.setState({ currentGenres: updatedGenres });
            })
            .catch(error => {
                if (error.response.status === 500) this.setState({ error: "Can't delete this row (constraint)" });
                else this.setState({ error: error.message });
            });
    }

    render() {
        const { currentGenres, totalGenres, currentPage, totalPages, isLoading, error } = this.state;

        if (totalGenres === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const genreList = currentGenres.map(genre => {
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

                <div className="w-100 px-4 pt-4 d-flex flex-row flex-wrap align-items-center justify-content-between">
                    <div className="d-flex flex-row align-items-center">
                        {currentPage && (
                            <span className="current-page d-inline-block h-100 pl-4">
                                Page <span className="font-weight-bold">{currentPage}</span> / <span className="font-weight-bold">{totalPages}</span>
                            </span>
                        )}

                    </div>

                    <div className="d-flex flex-row align-items-center">
                        <Pagination totalRecords={totalGenres} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/genres/new">Add genre</Button>
                        </div>
                        : null
                    }
                    <h4 style={{ color: 'red' }}>{error}</h4>
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