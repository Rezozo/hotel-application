import axios from "axios";

const PROFILE_API_HOME_URL = 'http://localhost:8080/profile/';
const PROFILE_API_UPDATE_URL = 'http://localhost:8080/profile/update';
const PROFILE_API_BOOKING_URL = 'http://localhost:8080/profile/booking';
const PROFILE_API_DELETE_BOOKING_URL = 'http://localhost:8080/profile/booking/delete';


class ProfileService {
    getProfileInfo(email: string) {
        return axios.get(PROFILE_API_HOME_URL, {params: {'email': email}});
    }
    updateProfileInfo(data ={}) {
        return axios.put(PROFILE_API_UPDATE_URL, data);
    }
    getAllBooking(email: string) {
        return axios.get(PROFILE_API_BOOKING_URL, {params: {'email': email}});
    }
    deleteBooking(id : number) {
        return axios.delete(PROFILE_API_DELETE_BOOKING_URL, {params: {'id': id}});
    }
}

export default new ProfileService();