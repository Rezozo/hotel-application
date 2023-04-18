import axios from "axios";

const ROOM_API_BASE_HOME_URL = 'http://localhost:8080/myhotel/';
const ROOM_API_ALL_ROOMS_URL = 'http://localhost:8080/myhotel/allrooms';

class RoomService {
    getTypes() {
        return axios.get(ROOM_API_BASE_HOME_URL);
    }
    getAll(status: string, direction: string, arrivalDate: Date | null, departireDate: Date | null) {
        return axios.get(ROOM_API_ALL_ROOMS_URL, { params: { 'direction': direction, 'status': status, 'arrivalDate': arrivalDate, ' departureDate': departireDate } });
    }
    getAllByType(typetitle: string, status: string, direction: string, arrivalDate: Date | null, departireDate: Date | null) {
        return axios.get(`${ROOM_API_BASE_HOME_URL}${typetitle}/rooms`, { params: { 'direction': direction, 'status': status, 'arrivalDate': arrivalDate, ' departureDate': departireDate } })
    }
    getOneByTitle(typetitle: string, title: string) {
        return axios.get(`${ROOM_API_BASE_HOME_URL}${typetitle}/rooms/${title}`)
    }
}

export default new RoomService();