export interface EUI {
    inform(text: string, callback?: Function): void

    warn(text: string, callback?: Function): void

    validateForm(vm: any, target: string): boolean

    confirm(text: string, successCallback?: Function, cancelCallback?: Function): void

    actWithValidation(vm: any, target: Array<String> | String, callback?: Function): void
}