import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';

class FilmCastList extends Component {

    constructor(props) {
        super(props);
        this.state = { filmCasts: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/filmCasts')
            .then(response => this.setState({ filmCasts: response.data }));
    }

    async remove(id) {
        await makeTokenizedRequest(`api/filmCast/delete/${id}`, 'DELETE').
            then(() => {
                let updatedFilmCasts = [...this.state.filmCasts].filter(i => i.id !== id);
                this.setState({ filmCasts: updatedFilmCasts });
            });
    }

    render() {
        const { filmCasts, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmCastList = filmCasts.map(filmCast => {
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