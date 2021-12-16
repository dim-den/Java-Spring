import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, getToken, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 3;

class ActorList extends Component {

    constructor(props) {
        super(props);
        this.state = { actors: [], currentActors: [], totalActors: 0, currentPage: null, totalPages: null, error: null};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/actors?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ actors: response.data }));

        makeTokenizedRequest(`/api/actors/count`)
            .then(response => this.setState({ totalActors: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/actors?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentActors = response.data;
                this.setState({ currentPage, currentActors, totalPages });
            });
    }

    async remove(id) {
        this.setState({ error: null })

        await makeTokenizedRequest(`api/actor/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedActors = [...this.state.actors].filter(i => i.id !== id);
                this.setState({ currentActors: updatedActors });
            })
            .catch(error => {
                if (error.response.status === 500) this.setState({ error: "Can't delete this row (constraint)" });
                else this.setState({ error: error.message });
            });
    }

    render() {
        const { currentActors, totalActors, currentPage, totalPages, isLoading, error } = this.state;

        if (totalActors === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const actorList = currentActors.map(actor => {
            return <tr key={actor.id}>
                <td>{actor.id}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{actor.name}</td>
                <td>{actor.surname}</td>
                <td>{actor.country}</td>
                <td>{actor.bday.substring(0, 10)}</td>
                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"actors/" + actor.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(actor.id)}>Delete</Button>
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
                        <Pagination totalRecords={totalActors} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/actors/new">Add actor</Button>
                        </div>
                        : null
                    }
                    <h4 style={{ color: 'red' }}>{error}</h4>
                    <h3>Actors</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>
                                <th width="20%">Name</th>
                                <th width="20%">Surname</th>
                                <th width="20%">Country</th>
                                <th width="15%">Birthday</th>
                            </tr>
                        </thead>
                        <tbody>
                            {actorList}
                        </tbody>
                    </Table>
                </Container>
            </div >
        );
    }
}
export default ActorList;