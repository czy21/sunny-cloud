const tokenKey = 'token'

export function getToken(): string | null {
    return localStorage.getItem(tokenKey)
}

export function setToken(value: string) {
    return localStorage.setItem(tokenKey, value);
}

export function delToken() {
    return localStorage.removeItem(tokenKey);
}
