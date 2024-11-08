export interface MenuModel {
    name: string,
    path?: string,
    icon?: string,
    children?: MenuModel[]
}

const menus: Array<MenuModel> = [
    {
        name: "首页",
        path: "/",
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
                name: "Vxe表格",
                path: "/sys/table/dynamic-vxe-table"
            }
        ]
    }
]

export default menus