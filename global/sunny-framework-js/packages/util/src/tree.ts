export const override = (t1: any, t2: any, idKey: string = "id", parentKey: string = "parentId") => {

    if (t1[idKey] === t2[idKey]) {
        let mergedNode: any = {...t1, ...t2}
        const childMap = new Map();

        for (const child of t1.children || []) {
            childMap.set(child[idKey], child);
        }

        for (const child of t2.children || []) {
            childMap.set(child[idKey], childMap.has(child[idKey]) ? override(childMap.get(child[idKey]), child) : child);
        }

        mergedNode.children = Array.from(childMap.values());
        return mergedNode;
    }

    return t1.children;
}

export const build = (all: any[], predicate: (value: any, index: number, array: any[]) => boolean, attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {}) => {
    attr = {idKey: "id", parentKey: "parentId", pathsKey: "paths", sortKey: "sort", ...attr}
    return all.filter(predicate).map((t: any) => buildChildren(all, t, attr)).sort((a, b) => a[attr.sortKey] - b[attr.sortKey])
}

export const buildChildren = (items: any[], node: any, attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {}) => {
    let children = items.filter((t: any) => node[attr.idKey] == t[attr.parentKey]).map((t: any) => buildChildren(items, t, attr))
    node['children'] = node['children'] ? [...node['children'], ...children] : children
    node.children.sort((a: any, b: any) => a[attr.sortKey] - b[attr.sortKey])
    return node
}

export const buildByPath = (all: any[], rootValue: any = null,
                            attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {},
                            decoFunc: (item: any, node: any, pathIndex: number) => {}) => {
    attr = {idKey: "id", parentKey: "parentId", pathsKey: "paths", sortKey: "sort", ...attr}
    let root: any = {}
    root[attr.idKey] = rootValue
    root["children"] = []
    all.forEach(t => processPath(root, t, attr, decoFunc))
    return root.children
}

export const processPath = (root: any, item: any,
                            attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {},
                            decoFunc: (item: any, node: any, pathIndex: number) => {}) => {
    let node = root
    item[attr.pathsKey].forEach((p: any, i: number, a: any[]) => {
        let pVal = typeof p == 'object' ? p[attr.idKey] : p
        node.children = node.children || []
        let child = node.children.find((c: any) => c[attr.idKey] == pVal)
        if (!child) {
            child = {}
            child[attr.idKey] = pVal
            child[attr.parentKey] = node[attr.idKey]
            child = i == a.length - 1 ? {...item, ...child} : child
            node.children.push(child)
            node.children.sort((a: any, b: any) => a[attr.sortKey] - b[attr.sortKey])
        }
        node = child
        if (typeof p === 'object') {
            Object.entries(p).forEach(([k, v]) => node[k] = v)
        }
        decoFunc && decoFunc(item, node, i)
    })
}