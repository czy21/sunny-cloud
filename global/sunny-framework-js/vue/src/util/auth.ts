import Cookies from "js-cookie";

const tokenKey = 'token'

export function getToken() {
    return Cookies.get(tokenKey)
}

export function setToken(value: string) {
    Cookies.set(tokenKey, value, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.')});
}

export function delToken() {
    Cookies.remove(tokenKey, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.')});
}
