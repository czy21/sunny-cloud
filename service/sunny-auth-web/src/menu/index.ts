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
        name: "测试",
        path: "/test-component"
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