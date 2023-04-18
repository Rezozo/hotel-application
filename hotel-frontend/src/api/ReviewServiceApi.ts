import axios from "axios";

const REVIEW_API_BASE_HOME_URL = 'http://localhost:8080/reviews/';
const REVIEW_API_ADD_REVIEW_URL = 'http://localhost:8080/reviews/add';
const REVIEW_API_DELETE_REVIEW_URL = 'http://localhost:8080/reviews/delete';

class ReviewService {
    getReviews() {
        return axios.get(REVIEW_API_BASE_HOME_URL);
    }
    addNewReview(data = {}) {
        return axios.post(REVIEW_API_ADD_REVIEW_URL, data);
    }
    deleteReview(id: string) {
        return axios.delete(REVIEW_API_DELETE_REVIEW_URL, { data: { id } });
    }
}

export default new ReviewService();