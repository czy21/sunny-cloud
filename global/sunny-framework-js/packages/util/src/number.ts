import {isEmpty} from "./object";


export const toMilliSeparator = (value: any, original?: boolean) => {
    let valueNumber = Number(value)
    if (isEmpty(value) || (!valueNumber && original)) {
        return value
    }
    return (valueNumber || Number(0)).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,')
}