import AuthServiceApi from "@/api/AuthServiceApi";
import axios from "axios";

export default function hasTokenOrRefresh() {
  const token = localStorage.getItem('authorizationToken');
  axios.defaults.headers.common['Authorization'] = token;
  if (token) {
    token.replace('Bearer ', '');
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    if (decodedToken.exp && decodedToken.exp > Math.floor(Date.now() / 1000)) {
      return true;
    } else {
      delete axios.defaults.headers.common['Authorization'];
      localStorage.removeItem('authorizationToken');
      return AuthServiceApi.refresh(localStorage.getItem('refreshToken'))
        .then(response => {
          const newToken = response.data.token;
          localStorage.setItem('authorizationToken', 'Bearer ' + newToken);
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + newToken;
          return true;
        })
        .catch(error => {
          console.error('Failed to refresh token:', error);
          return false;
        });
    }
  }
}

export function setToken() {
  const token = localStorage.getItem('authorizationToken');
  axios.defaults.headers.common['Authorization'] = token;
}

export function logout() {
  AuthServiceApi.logout()
    .then(() => {
      localStorage.removeItem('authorizationToken');
      localStorage.removeItem('refreshToken');
      window.location.reload();
    })
    .catch(error => {
      console.error('Failed to logout:', error);
    });
}

export function checkUserName(email: string) {
  const token = localStorage.getItem('authorizationToken');
  if (token) {
    token.replace('Bearer ', '');
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    if (decodedToken && decodedToken.sub == email) {
      return true;
    }
  }
  return false
}

export function getEmail() {
  const token = localStorage.getItem('authorizationToken');
  if (token) {
    token.replace('Bearer ', '');
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    return decodedToken.sub;
  }
}