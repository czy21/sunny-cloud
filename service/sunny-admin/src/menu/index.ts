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
        icon: "Setting",
        children: [
            {
                name: "动态表头",
                path: "/sys/table/dynamic-head"
            },
        ]
    }
]

export default menus