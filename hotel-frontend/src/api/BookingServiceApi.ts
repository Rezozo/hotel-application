import axios from "axios";

const BOOKING_API__URL = 'http://localhost:8080/booking/';

class BookingService {
    addNewBooking(data = {}) {
        return axios.post(BOOKING_API__URL, data);
    }
}

export default new BookingService();