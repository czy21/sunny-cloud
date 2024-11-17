import Cookies, {CookieAttributes} from "js-cookie";

export const getToken = (tokenKey = "token") => {
    return Cookies.get(tokenKey)
}

export const setToken = (tokenKey = "token", value: string, options?: CookieAttributes) => {
    Cookies.set(tokenKey, value, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.'), ...options});
}

export const delToken = (tokenKey = "token", options?: CookieAttributes) => {
    Cookies.remove(tokenKey, {domain: '.' + window.location.hostname.split('.').slice(-2).join('.'), ...options});
}
