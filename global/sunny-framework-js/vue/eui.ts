import _ from 'lodash'
import util from '@sunny-framework-js/util'
import {MessageBox} from "element-plus";

export interface EUI {
    inform(text: string, callback?: Function): void

    warn(text: string, callback?: Function): void

    validateForm(vm: any, target: string): boolean

    confirm(text: string, successCallback?: Function, cancelCallback?: Function): void

    actWithValidation(vm: any, target: Array<String> | String, callback?: Function): void
}

const inform = function (text: string, callback?: Function) {
    MessageBox.alert(text, '提示', {
        type: 'info',
        dangerouslyUseHTMLString: true,
        showClose: false
    }).finally(() => {
        util.basic.callIfExists(callback)
    })
}

const warn = function (text: string, callback?: Function) {
    MessageBox.alert(text, '警告', {
        type: 'warning',
        dangerouslyUseHTMLString: true,
        showClose: false
    }).finally(() => {
        util.basic.callIfExists(callback)
    })
}

const confirm = function (text: string, successCallback?: Function, cancelCallback?: Function) {
    MessageBox.confirm(text, '提示', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        util.basic.callIfExists(successCallback)
    }).catch(() => {
        util.basic.callIfExists(cancelCallback)
    })
}

const validateForm = function (vm: any, target: string) {
    let res = false
    vm.$refs[target].validate((valid: boolean) => {
        res = valid
    })
    return res
}

const actWithValidation = function (vm: any, target: Array<String> | String, callback?: Function) {

    let targets = _.isString(target) ? new Array(target) : target

    let valid = _.every(targets, (v: string) => {
        return validateForm(vm, v)
    })
    valid ? util.basic.callIfExists(callback) : warn("请检查输入的参数再执行操作")
}

export default {inform, warn, confirm, validateForm, actWithValidation}

