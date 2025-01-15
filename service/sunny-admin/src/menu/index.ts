import {MenuModel} from "@sunny-framework-js/vue";

const menus: Array<MenuModel> = [
    {
        name: "首页",
        path: "/dashboard",
        icon: "HomeFilled"
    },
    {
        name: "用户管理",
        path: "/sys/user",
        icon: "User"
    },
    {
        name: "系统管理",
        icon: "Setting",
        children: [
            {
                name: "菜单管理",
                path: "/sys/menu",
                icon: "Menu"
            },
        ]
    },
    {
        name: "Table",
        children: [
            {
                name: "EL表格",
                path: "/sys/table/dynamic-el-table"
            },
            {
                name: "虚拟表格",
                path: "/sys/table/dynamic-virtual-table"
            },
            {
                name: "Vxe表格",
                path: "/sys/table/dynamic-vxe-table"
            },
            {
                name: "Naive表格",
                path: "/sys/table/dynamic-naive-table"
            }
        ]
    }
]

export default menus