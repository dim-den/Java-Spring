import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, getToken, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class FilmList extends Component {

    constructor(props) {
        super(props);
        this.state = { films: [], currentFilms: [], totalFilms: 0, currentPage: null, totalPages: null, error: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/films?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ films: response.data }));

        makeTokenizedRequest(`/api/films/count`)
            .then(response => this.setState({ totalFilms: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/films?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentFilms = response.data;
                this.setState({ currentPage, currentFilms, totalPages });
            });
    }

    async remove(id) {
        this.setState({ error: null })

        await makeTokenizedRequest(`api/film/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedfilms = [...this.state.films].filter(i => i.id !== id);
                this.setState({ currentFilms: updatedfilms });
            }) 
            .catch(error => {
                if (error.response.status === 500) this.setState({ error: "Can't delete this row (constraint)"});
                else this.setState({ error: error.message });
            });
    }   

    render() {
        const { currentFilms, totalFilms, currentPage, totalPages, isLoading, error } = this.state;

        if (totalFilms === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmList = currentFilms.map(film => {
            return <tr key={film.id}>
                <td>{film.id}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{film.title}</td>
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

                <div className="w-100 px-4 pt-4 d-flex flex-row flex-wrap align-items-center justify-content-between">
                    <div className="d-flex flex-row align-items-center">
                        {currentPage && (
                            <span className="current-page d-inline-block h-100 pl-4">
                                Page <span className="font-weight-bold">{currentPage}</span> / <span className="font-weight-bold">{totalPages}</span>
                            </span>
                        )}

                    </div>

                    <div className="d-flex flex-row align-items-center">
                        <Pagination totalRecords={totalFilms} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/Films/new">Add Film</Button>
                        </div>
                        : null
                    }
                    <h4 style={{ color: 'red' }}>{error}</h4>
                    <h3>Films</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>
                                <th width="10%">Title</th>
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
            </div >
        );
    }
}
export default FilmList;