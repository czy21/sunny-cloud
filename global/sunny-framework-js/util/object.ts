


export function getValueByExpression(obj: any, expression: string) {
    return Function("obj", "return " + expression)(obj)
}