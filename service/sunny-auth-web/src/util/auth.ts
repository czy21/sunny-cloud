const tokenKey = 'token'

export function getToken(): string | null {
    document.domain = window.location.hostname.split('.').slice(-2).join('.')
    return localStorage.getItem(tokenKey)
}

export function setToken(value: string) {
    document.domain = window.location.hostname.split('.').slice(-2).join('.')
    return localStorage.setItem(tokenKey, value);
}

export function delToken() {
    document.domain = window.location.hostname.split('.').slice(-2).join('.')
    return localStorage.removeItem(tokenKey);
}
