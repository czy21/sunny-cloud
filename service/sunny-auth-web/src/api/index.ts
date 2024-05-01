import axios, {Method} from 'axios'
import util from "@/util";

//配置API接口地址
const root = 'api'

const service = axios.create({
    baseURL: root,
    timeout: 5000, //请求超时
    withCredentials: true
});
service.interceptors.request.use(
    config => {
        config.headers.Authorization = util.auth.getToken()
        return config;
    },
    error => {
        Promise.reject(error)
    });

service.interceptors.response.use(
    response => {
        return response
    },
    error => {
        Promise.reject(error)
    });

function apiAxios(method: Method, url: string, params: any): Promise<any> {
    return new Promise((resolve, reject) => {
        service({
            method,
            url: url,
            data: method === 'POST' || method === 'PUT' ? params : null,
            params: method === 'GET' || method === 'DELETE' ? params : null
        }).then(res => resolve(res), error => reject(error)).catch(error => reject(error))
    })
}

export default {
    get: function (url: string, params: any) {
        return apiAxios('GET', url, params)
    },
    post: function (url: string, params: any) {
        return apiAxios('POST', url, params)
    },
    put: function (url: string, params: any) {
        return apiAxios('PUT', url, params)
    },
    delete: function (url: string, params: any) {
        return apiAxios('DELETE', url, params)
    }
}


export interface API {
    get(url: string, param?: any): Promise<any>

    post(url: string, param?: any): Promise<any>

    put(url: string, param?: any): Promise<any>

    delete(url: string, param?: any): Promise<any>
}