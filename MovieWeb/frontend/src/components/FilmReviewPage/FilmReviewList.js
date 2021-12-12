import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './../Navbar/AppNavbar';
import { Link } from 'react-router-dom';
import { haveAccess, makeTokenizedRequest } from './../../utils/Common';
import Pagination from './../Pagination/Pagination';

const pageSize = 10;

class FilmReviewList extends Component {

    constructor(props) {
        super(props);
        this.state = { filmReviews: [], currentFilmReviews: [], totalFilmReviews: 0, currentPage: null, totalPages: null };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        makeTokenizedRequest(`/api/filmReviews?page=${0}&size=${pageSize}`)
            .then(response => this.setState({ filmReviews: response.data }));

        makeTokenizedRequest(`/api/filmReviews/count`)
            .then(response => this.setState({ totalFilmReviews: response.data }));
    }

    onPageChanged = data => {
        const { currentPage, totalPages, pageLimit } = data;

        makeTokenizedRequest(`/api/filmReviews?page=${currentPage - 1}&size=${pageLimit}`)
            .then(response => {
                const currentFilmReviews = response.data;
                this.setState({ currentPage, currentFilmReviews, totalPages });
            });
    }

    async remove(id) {
        await makeTokenizedRequest(`api/filmReview/delete/${id}`, 'DELETE')
            .then(() => {
                let updatedFilmReviews = [...this.state.filmReviews].filter(i => i.id !== id);
                this.setState({ currentFilmReviews: updatedFilmReviews });
            });
    }

    render() {
        const { currentFilmReviews, totalFilmReviews, currentPage, totalPages, isLoading } = this.state;

        if (totalFilmReviews === 0) return null;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const filmReviewList = currentFilmReviews.map(filmReview => {
            return <tr key={filmReview.id}>
                <td>{filmReview.id}</td>
                <td style={{ whiteSpace: 'wrap', maxWidth: '700px' }}>{filmReview.review}</td>
                <td>{filmReview.score}</td>
                <td>{filmReview.published.substring(0, 10)}</td>
                <td>{filmReview.userId}</td>
                <td>{filmReview.filmId}</td>

                {haveAccess("ADMIN") ?
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"filmReviews/" + filmReview.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(filmReview.id)}>Delete</Button>
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
                        <Pagination totalRecords={totalFilmReviews} pageLimit={pageSize} pageNeighbours={1} onPageChanged={this.onPageChanged} />
                    </div>
                </div>
                <Container fluid>
                    {haveAccess("ADMIN") ?
                        <div className="float-right">
                            <Button color="success" tag={Link} to="/filmReviews/new">Add film review</Button>
                        </div>
                        : null
                    }
                    <h3>Film reviews</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="5%">ID</th>
                                <th width="50%">Review</th>
                                <th width="5%">Score</th>
                                <th width="10%">Published</th>
                                <th width="10%">User ID</th>
                                <th width="10%">Film ID</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filmReviewList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default FilmReviewList;