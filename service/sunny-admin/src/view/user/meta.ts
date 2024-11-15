import {TableColumn} from '@sunny-framework-js/vue'

export const getListColumns = (): TableColumn[] => {
    return [
        {
            prop: "username",
            name: "账号"
        },
        {
            prop:"name",
            name:"姓名"
        },
        {
            prop: "phone",
            name: "手机号"
        },
        {
            prop:"email",
            name: "邮箱"
        },
        {
            prop: "address",
            name: "地址"
        }
    ]
}