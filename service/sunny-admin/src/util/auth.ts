import Cookies from "js-cookie";

const tokenKey = 'token'

export function getToken(): string | null {
    Cookies.get(tokenKey);
    return localStorage.getItem(tokenKey)
}

export function setToken(value: string) {
    Cookies.set(tokenKey, value, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.')});
    return localStorage.setItem(tokenKey, value);
}

export function delToken() {
    Cookies.remove(tokenKey, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.')});
    return localStorage.removeItem(tokenKey);
}
