import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class FilmCastList extends Component {

    constructor(props) {
        super(props);
        this.state = { filmCasts: [], currentFilmCasts: [], totalFilmCasts: 0, currentPage: null, totalPages: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/filmCasts?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ filmCasts: response.data }));

        makeTokenizedRequest(`/api/filmCasts/count`)
            .then(response => this.setState({ totalFilmCasts: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/filmCasts?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentFilmCasts = response.data;
                this.setState({ currentPage, currentFilmCasts, totalPages });
            });
    }

    async remove(id) {
        await makeTokenizedRequest(`api/filmCast/delete/${id}`, 'DELETE').
            then(() => {
                let updatedFilmCasts = [...this.state.filmCasts].filter(i => i.id !== id);
                this.setState({ currentFilmCasts: updatedFilmCasts });
            });
    }


    render() {
        const { currentFilmCasts, totalFilmCasts, currentPage, totalPages, isLoading } = this.state;

        if (totalFilmCasts === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmCastList = currentFilmCasts.map(filmCast => {
            return <tr key={filmCast.id}>
                <td>{filmCast.id}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{filmCast.roleType}</td>
                <td>{filmCast.roleName}</td>
                <td>{filmCast.actorId}</td>
                <td>{filmCast.filmId}</td>
                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"filmCasts/" + filmCast.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(filmCast.id)}>Delete</Button>
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
                        <Pagination totalRecords={totalFilmCasts} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/filmCasts/new">Add film cast</Button>
                        </div>
                        : null
                    }
                    <h3>Film cast</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>
                                <th width="20%">Role type</th>
                                <th width="20%">Role name</th>
                                <th width="10%">Actor ID</th>
                                <th width="10%">Film ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filmCastList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default FilmCastList;