import axios from "axios";

const AUTH_API_REGISTER_URL = 'http://localhost:8080/auth/register';
const AUTH_API_LOGIN_URL = 'http://localhost:8080/auth/authenticate';
const AUTH_API_LOGOUT_URL = 'http://localhost:8080/auth/logout';
const AUTH_API_REFRESH_TOKEN_URL = 'http://localhost:8080/auth/refresh';

class AuthServiceApi {
    register(data = {}) {
        return axios.post(AUTH_API_REGISTER_URL, data);
    }
    login(data = {}) {
        return axios.post(AUTH_API_LOGIN_URL, data);
    }
    logout() {
        return axios.get(AUTH_API_LOGOUT_URL);
    }
    refresh(refreshToken: any) {
        return axios.post(AUTH_API_REFRESH_TOKEN_URL, { refreshToken });
    }
}

export default new AuthServiceApi();