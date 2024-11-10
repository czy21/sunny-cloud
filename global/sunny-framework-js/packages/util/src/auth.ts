import Cookies from "js-cookie";

const tokenKey = 'token'

export const getToken = () => {
    return Cookies.get(tokenKey)
}

export const setToken = (value: string) => {
    Cookies.set(tokenKey, value, { domain: '.' + window.location.hostname.split('.').slice(-2).join('.') });
}

export const delToken = () => {
    Cookies.remove(tokenKey, { domain: '.' + window.location.hostname.split('.').slice(-2).join('.') });
}
