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

export const buildByPath = (all: any[], rootValue: any = null, attr: any = {}, decoFunc: (item: any, node: any, pathIndex: number) => {}) => {
    attr = {
        pathsKey: "paths",
        idKey: "id",
        parentKey: "parentId",
        sortKey: "sort",
        ...attr
    }
    let root: any = {}
    root[attr.idKey] = rootValue
    root["children"] = []
    all.forEach(t => processPath(root, t, attr, decoFunc))
    return root.children
}

export const processPath = (root: any, item: any, attr: any = {}, decoFunc: (item: any, node: any, pathIndex: number) => {}) => {
    attr = {
        pathsKey: "paths",
        idKey: "id",
        parentKey: "parentId",
        sortKey: "sort",
        ...attr
    }
    let node = root
    item[attr.pathsKey].forEach((t: any, i: number, a: any[]) => {
        node.children = node.children || []
        let child = node.children.find((c: any) => c[attr.idKey] == (typeof t == 'object' ? t[attr.idKey] : t))
        if (!child) {
            child = {}
            child[attr.idKey] = typeof t === 'object' ? t[attr.idKey] : t
            child[attr.parentKey] = node[attr.idKey]
            child = i == a.length - 1 ? {...item, ...child} : child
            node.children.push(child)
            node.children.sort((a: any, b: any) => a[attr.sortKey] - b[attr.sortKey])
        }
        node = typeof t === 'object' ? {...child, ...t} : child
        decoFunc && decoFunc(item, node, i)
    })
}

export const build = (all: any[], predicate: (value: any, index: number, array: any[]) => unknown, thisArg?: any, idKey: string = "id", parentKey: string = "parentId", sortKey: string = "sort") => {
    return all.filter(predicate).map((t: any) => buildChildren(all, t, idKey, parentKey)).sort((a, b) => a[sortKey] - b[sortKey])
}

export const buildChildren = (items: any[], node: any, idKey: string = "id", parentKey: string = "parentId", sortKey: string = "sort") => {
    let children = items.filter((t: any) => node[idKey] == t[parentKey]).map((t: any) => buildChildren(items, t, idKey, parentKey))
    node['children'] = node['children'] ? [...node['children'], ...children] : children
    node.children.sort((a: any, b: any) => a[sortKey] - b[sortKey])
    return node
}