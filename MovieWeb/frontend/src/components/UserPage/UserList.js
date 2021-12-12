import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';

class UserList extends Component {

    constructor(props) {
        super(props);
        this.state = { users: [] };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest('/api/users')
            .then(response => this.setState({ users: response.data }));
    }

    async remove(id) {
        await makeTokenizedRequest(`api/user/delete/${id}`, 'DELETE')
        .then(() => {
            let updatedUsers = [...this.state.users].filter(i => i.id !== id);
            this.setState({ users: updatedUsers });
        });
    }

    render() {
        const { users, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const userList = users.map(user => {
            return <tr key={user.id}>
                 <td>{user.id}</td>
                <td style={{ whiteSpace: 'nowrap' }}>{user.email}</td>
                <td>{user.username}</td>
                <td>{user.passwordHash}</td>
                <td>{user.role}</td>
                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"users/" + user.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(user.id)}>Delete</Button>
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
                            <Button color="success" tag={Link} to="/users/new">Add user</Button>
                        </div>
                        : null
                    }
                    <h3>Users</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>    
                                <th width="20%">Email</th>
                                <th width="20%">Username</th>
                                <th width="30%">Password</th>
                                <th width="15%">Role</th>
                            </tr>
                        </thead>
                        <tbody>
                            {userList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default UserList;