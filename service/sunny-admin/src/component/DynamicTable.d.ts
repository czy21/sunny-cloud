import {TableColumnCtx, IDatePickerType} from "element-plus";

declare module 'element-plus' {
    export interface TableColumnCtx<T> {
        node: TableColumn
    }
}

export interface TableProps {
    columns: TableColumn,
    data: Array<any>,
    dict: DictType,
    subTotal: SubTotalType
}

export interface TableColumn {
    prop: string
    name: string
    type?: string | "string" | "number" | "select" | IDatePickerType
    parentProp?: string
    parentName?: string
    heads?: string[]
    editable?: boolean
    dictKey?: string
    rowTotal?: string
    colTotal?: boolean
    width?: string | number
    fixed?: string
    custom?: boolean
}

export interface DictType {
    [key: string]: Array<{ label: string, value: Object }>
}

export interface SubTotalType {
    [key: string]: { groupBy(item: Object): boolean }
}