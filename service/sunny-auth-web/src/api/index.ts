import axios, {AxiosRequestConfig} from 'axios'
import util from "@/util";
import router from "@/router";
import {ElMessage} from "element-plus";


enum Method {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE"
}

const service = axios.create({
    baseURL: '/api',
    withCredentials: true
});

service.interceptors.request.use(
    config => {
        config.headers.Authorization = !config.url?.includes("login") && util.auth.getToken()
        return config;
    },
    error => {
        return Promise.reject(error)
    });
service.interceptors.response.use(
    response => {
        const {code, message} = response.data
        switch (code) {
            case 400100:
                ElMessage.error(message)
                break
            case 400401:
                router.push({path: "/login"})
                break
        }
        return response
    },
    error => {
        const {status} = error.response || {};
        switch (status) {
            case 401:
                router.push({path: "/login"})
                break
            case 500:
                ElMessage.error("服务器异常")
                break
            case 504:
                ElMessage.error("网络超时")
                break
        }
        return Promise.reject(error)
    });

function apiAxios(method: Method, url: string, params: any, config?: AxiosRequestConfig, errorCallBack?: (error: any) => void) {
    return new Promise((resolve, reject) => {
        service({
            method: method,
            url: url,
            data: method === 'POST' || method === 'PUT' ? params : null,
            params: method === 'GET' || method === 'DELETE' ? params : null,
            ...config
        })
            .then(res => resolve(res))
            .catch(error => {
                if (errorCallBack) {
                    errorCallBack(error)
                }
            })
    })
}

export default {
    get: (url: string, params?: any, config?: AxiosRequestConfig, errorCallBack?: (error: any) => void) => {
        return apiAxios(Method.GET, url, params, config, errorCallBack)
    },
    post: (url: string, params?: any, config?: AxiosRequestConfig, errorCallBack?: (error: any) => void) => {
        return apiAxios(Method.POST, url, params, config, errorCallBack)
    },
    put: (url: string, params?: any, config?: AxiosRequestConfig, errorCallBack?: (error: any) => void) => {
        return apiAxios(Method.PUT, url, params, config, errorCallBack)
    },
    delete: (url: string, params?: any, config?: AxiosRequestConfig, errorCallBack?: (error: any) => void) => {
        return apiAxios(Method.DELETE, url, params, config, errorCallBack)
    }
};
