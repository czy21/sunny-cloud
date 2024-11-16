import {CSSProperties} from "vue";

declare module 'element-plus' {
    export interface TableColumnCtx<T> {
        node: TableColumn
    }
}

export interface TableProps {
    columns: Array<TableColumn>,
    data: Array<any>,
    dict: DictType,
    subTotal: SubTotalType
    editable?: boolean

    showSummary?: boolean

    handleInput?: any
    handleSelect?: any
    handleSelectSearch?: any
}

export interface TableColumn {
    prop: string
    name: string
    type?: string | "string" | "number" | "select"
    parentProp?: string
    parentName?: string
    required?: boolean
    editable?: boolean | string
    dictKey?: string
    dictPush?: [string, object]
    rowTotal?: string
    colTotal?: boolean
    heads?: (string | TableHead)[]
    fixed?: string
    remote?: boolean
    custom?: boolean
}

export interface TableHead {
    name: string
    style?: CSSProperties
}

export interface DictType {
    [key: string]: Array<{ label: string, value: Object, extra: Object }>
}

export interface SubTotalType {
    [key: string]: { groupBy(item: Object, data: { columns: any[], data: any[] }): boolean, byValue: boolean }
}