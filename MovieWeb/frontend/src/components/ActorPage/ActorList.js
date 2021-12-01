import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';

class ActorList extends Component {

    constructor(props) {
        super(props);
        this.state = {actors: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/api/actors')
            .then(response => response.json())
            .then(data => this.setState({actors: data}));
    }

    async remove(id) {
        await fetch(`api/actor/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedActors = [...this.state.actors].filter(i => i.id !== id);
            this.setState({actors: updatedActors});
        });
    }
    
    render() {
        const {actors, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }
    
        const actorList = actors.map(actor => {
            return <tr key={actor.id}>
                <td style={{whiteSpace: 'nowrap'}}>{actor.name}</td>
                <td>{actor.surname}</td>
                <td>{actor.country}</td>
                <td>{actor.bday.substring(0, 10)}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"actors/" + actor.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(actor.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });
    
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/actors/new">Add actor</Button>
                    </div>
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