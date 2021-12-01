import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';

class FilmCastList extends Component {

    constructor(props) {
        super(props);
        this.state = {filmCasts: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/api/filmCasts')
            .then(response => response.json())
            .then(data => this.setState({filmCasts: data}));
    }

    async remove(id) {
        await fetch(`api/filmCast/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedFilmCasts = [...this.state.filmCasts].filter(i => i.id !== id);
            this.setState({filmCasts: updatedFilmCasts});
        });
    }
    
    render() {
        const {filmCasts, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading...</p>;
        }
    
        const filmCastList = filmCasts.map(filmCast => {
            return <tr key={filmCast.id}>
                <td style={{whiteSpace: 'nowrap'}}>{filmCast.roleType}</td>
                <td>{filmCast.roleName}</td>
                <td>{filmCast.actorId}</td>
                <td>{filmCast.filmId}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"filmCasts/" + filmCast.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(filmCast.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });
    
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/filmCasts/new">Add film cast</Button>
                    </div>
                    <h3>Film cast</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
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