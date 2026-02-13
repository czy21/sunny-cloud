import {MenuModel} from "@sunny-framework-js/vue";

const menus: Array<MenuModel> = [
    {
        code: 'Dashboard',
        name: '首页',
        path: '/dashboard',
        component: () => import('@v/dashboard.vue'),
        type: 'MENU',
        extra: {
            meta: {
                affix: true
            }
        }
    }
]

export default menus

export const DynamicMenus: Array<MenuModel> = [
    {
        code: "UserManage",
        name: "用户管理",
        path: "/sys/user",
        component: 'user/index.vue',
        type: 'MENU',
        icon: "User"
    },
    // {
    //     name: "聊天管理",
    //     path: "/sys/chat",
    //     icon: "Chat"
    // },
    // {
    //     name: "系统管理",
    //     icon: "Setting",
    //     children: [
    //         {
    //             name: "菜单管理",
    //             path: "/sys/menu",
    //             icon: "Menu"
    //         },
    //     ]
    // },
    // {
    //     name: "Table",
    //     children: [
    //         {
    //             name: "EL表格",
    //             path: "/sys/table/dynamic-el-table"
    //         },
    //         {
    //             name: "Vxe表格",
    //             path: "/sys/table/dynamic-vxe-table"
    //         },
    //         {
    //             name: "Naive表格",
    //             path: "/sys/table/dynamic-naive-table"
    //         }
    //     ]
    // }
]