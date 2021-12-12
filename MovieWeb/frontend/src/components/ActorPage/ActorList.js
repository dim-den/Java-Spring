import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, getToken, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class ActorList extends Component {

    constructor(props) {
        super(props);
        this.state = { actors: [], currentActors: [], totalActors: 0, currentPage: null, totalPages: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/actors?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ actors: response.data}));

        makeTokenizedRequest(`/api/actors/count`)
            .then(response => this.setState({totalActors: response.data }));
    }

    onPageChanged = data => {
        // const { actors } = this.state;
        // const { currentPage, totalPages, pageLimit } = data;

        // const offset = (currentPage - 1) * pageLimit;
        // const currentActors = actors.slice(offset, offset + pageLimit);

        // this.setState({ currentPage, currentActors, totalPages });

        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/actors?page=${currentPage-1}&size=${pageLimit}`)
            .then(response => {
                const currentActors = response.data;
                this.setState({ currentPage, currentActors, totalPages });
            });
    }

    async remove(id) {
        await makeTokenizedRequest(`api/actor/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedActors = [...this.state.actors].filter(i => i.id !== id);
                this.setState({ actors: updatedActors });
            });
    }

    render() {
        const { currentActors, totalActors, currentPage, totalPages, isLoading } = this.state;

        if (totalActors === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const actorList = currentActors.map(actor => {
            return <tr key={actor.id}>
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
                    {haveAccess("ADMINN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/actors/new">Add actor</Button>
                        </div>
                        : null
                    }
                    <h3>Actors</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
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