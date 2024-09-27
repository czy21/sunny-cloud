export function buildByPath(all: any[],
                            rootValue: any = null,
                            pathsKey: string = "paths",
                            idKey: string = "id",
                            parentKey: string = "parentId",
                            sortKey: string = "sort") {
    let root: any = {}
    root[idKey] = rootValue
    root["children"] = []
    all.forEach(t => processPath(root, t, pathsKey, idKey, parentKey, sortKey))
    return Object.values(root.children.sort((a: any, b: any) => a[sortKey] - b[sortKey]))
}

export function processPath(root: any,
                            node: any,
                            pathsKey: string = "paths",
                            idKey: string = "id",
                            parentKey: string = "parentId",
                            sortKey: string = "sort") {
    let current = root
    node[pathsKey].forEach((t: any, i: number, a: any[]) => {
        current.children = current.children || []
        let child = current.children.find((c: any) => c[idKey] == t)
        if (!child) {
            child = {}
            child[idKey] = t
            child[parentKey] = current[idKey]
            child = i == a.length - 1 ? {...node, ...child} : child
            current.children.push(child)
            current.children = current.children.sort((a: any, b: any) => a[sortKey] - b[sortKey])
        }
        current = child
    })
}

export function build(all: any[],
                      predicate: (value: any, index: number, array: any[]) => unknown, thisArg?: any,
                      idKey: string = "id",
                      parentKey: string = "parentId",
                      sortKey: string = "sort") {
    return all.filter(predicate).map((t: any) => buildChildren(all, t, idKey, parentKey)).sort((a, b) => a[sortKey] - b[sortKey])
}

export function buildChildren(items: any[],
                              node: any,
                              idKey: string = "id",
                              parentKey: string = "parentId",
                              sortKey: string = "sort") {
    let children = items.filter((t: any) => node[idKey] == t[parentKey]).map((t: any) => buildChildren(items, t, idKey, parentKey))
    node['children'] = node['children'] ? [...node['children'], ...children] : children
    node.children.sort((a: any, b: any) => a[sortKey] - b[sortKey])
    return node
}