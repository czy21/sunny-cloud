export interface MenuModel {
    path?: string,
    name: string,
    icon: string,
    children?: MenuModel[]
}

const menus: Array<MenuModel> = [
    {
        name: "系统管理",
        icon: "el-icon-setting",
        children: [
            {
                name: "人员管理",
                path: "user",
                icon: "el-icon-user"
            },
            {
                name: "菜单管理",
                path: "menu",
                icon: "el-icon-user"
            },
        ]
    },
    {
        name: "其他管理",
        path: "/other",
        icon: "el-icon-more"
    }
]

export default menus