import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, getToken, makeTokenizedRequest } from './../../utils/Common';

class ActorList extends Component {

    constructor(props) {
        super(props);
        this.state = { actors: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/actors')
            .then(response => this.setState({ actors: response.data }));
    }

    async remove(id) {
        await makeTokenizedRequest(`api/actor/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedActors = [...this.state.actors].filter(i => i.id !== id);
                this.setState({ actors: updatedActors });
            });
    }

    render() {
        const { actors, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const actorList = actors.map(actor => {
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
            </div>
        );
    }
}
export default ActorList;