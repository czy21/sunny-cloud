import {helper} from '@sunny-framework-js/vue'
import type {HttpClientOption} from "@sunny-framework-js/vue";
import router from '@/router'
import {ElMessage} from 'element-plus'

const api = new helper.api.HttpClient({
    handleResponse: (response) => {
        const {code, message} = response.data
        // if (code === 1401) {
        //     ElMessage.error(message)
        //     setTimeout(() => {
        //         router.push('/workbench')
        //     }, 3000)
        // }
    }
} as HttpClientOption)
export default {
    ...helper,
    api
}