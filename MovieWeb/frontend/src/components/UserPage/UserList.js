import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class UserList extends Component {

    constructor(props) {
        super(props);
        this.state = { users: [], currentUsers: [], totalUsers: 0, currentPage: null, totalPages: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/users?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ users: response.data }));

        makeTokenizedRequest(`/api/users/count`)
            .then(response => this.setState({ totalUsers: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/users?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentUsers = response.data;
                this.setState({ currentPage, currentUsers, totalPages });
            });
    }

    async remove(id) {
        await makeTokenizedRequest(`api/user/delete/${id}`, 'DELETE')
        .then(() => {
            let updatedUsers = [...this.state.users].filter(i => i.id !== id);
            this.setState({ currentUsers: updatedUsers });
        });
    }

    render() {
        const { currentUsers, totalUsers, currentPage, totalPages, isLoading } = this.state;

        if (totalUsers === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const userList = currentUsers.map(user => {
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

                <div className="w-100 px-4 pt-4 d-flex flex-row flex-wrap align-items-center justify-content-between">
                    <div className="d-flex flex-row align-items-center">
                        {currentPage && (
                            <span className="current-page d-inline-block h-100 pl-4">
                                Page <span className="font-weight-bold">{currentPage}</span> / <span className="font-weight-bold">{totalPages}</span>
                            </span>
                        )}

                    </div>

                    <div className="d-flex flex-row align-items-center">
                        <Pagination totalRecords={totalUsers} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
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