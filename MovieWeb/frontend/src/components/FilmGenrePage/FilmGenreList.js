import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class FilmGenreList extends Component {

    constructor(props) {
        super(props);
        this.state = { filmGenres: [], currentFilmGenres: [], totalFilmGenres: 0, currentPage: null, totalPages: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/filmGenres?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ FilmGenres: response.data }));

        makeTokenizedRequest(`/api/filmGenres/count`)
            .then(response => this.setState({ totalFilmGenres: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/filmGenres?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentFilmGenres = response.data;
                this.setState({ currentPage, currentFilmGenres, totalPages });
            });
    }

    async remove(id) {
        await makeTokenizedRequest(`api/filmGenre/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedFilmGenres = [...this.state.filmGenres].filter(i => i.id !== id);
                this.setState({ currentFilmGenres: updatedFilmGenres });
            });
    }

    render() {
        const { currentFilmGenres, totalFilmGenres, currentPage, totalPages, isLoading } = this.state;

        if (totalFilmGenres === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmGenreList = currentFilmGenres.map(filmGenre => {
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

                <div className="w-100 px-4 pt-4 d-flex flex-row flex-wrap align-items-center justify-content-between">
                    <div className="d-flex flex-row align-items-center">


                        {currentPage && (
                            <span className="current-page d-inline-block h-100 pl-4">
                                Page <span className="font-weight-bold">{currentPage}</span> / <span className="font-weight-bold">{totalPages}</span>
                            </span>
                        )}

                    </div>

                    <div className="d-flex flex-row align-items-center">
                        <Pagination totalRecords={totalFilmGenres} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
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