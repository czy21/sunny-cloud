


export const getValueByExpression = (obj: any, expression: string) => {
    return Function("obj", "return " + expression)(obj)
}