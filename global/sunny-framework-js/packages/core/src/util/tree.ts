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

export const build = (all: any[], attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {}, decoFunc?: (node: any) => void) => {
    attr = {idKey: "id", parentKey: "parentId", pathsKey: "paths", sortKey: "sort", ...attr}
    let root = {}
    buildChildren(all, root, attr, decoFunc, 0)
    return root.children
}

export const buildChildren = (all: any[], node: any, attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {}, decoFunc: (node: any) => void, level: number) => {
    node.children = node.children || []
    for (var t of all) {
        if (node[attr.idKey] == t[attr.parentKey]) {
            buildChildren(all, t, attr, decoFunc, level + 1);
            node.children.push(t)
        }
    }
    decoFunc && decoFunc(node)
    node.children?.sort((a: any, b: any) => a[attr.sortKey] - b[attr.sortKey])
    return node
}

export const buildByPath = (all: any[], attr: { idKey?: string, parentKey?: string, pathsKey?: string, sortKey?: string } = {}, decoFunc?: (item: any, node: any, pathIndex: number) => void) => {
    attr = {idKey: "id", parentKey: "parentId", pathsKey: "paths", sortKey: "sort", ...attr}
    let root: any = {}
    for (var t of all) {
        let current = root
        t[attr.pathsKey]?.forEach((p: any, i: number, a: any[]) => {
            current.children = current.children || []
            let pVal = typeof p == 'object' ? p[attr.idKey] : p
            let child = current.children.find((c: any) => c[attr.idKey] == pVal)
            if (!child) {
                child = {}
                child[attr.idKey] = pVal
                child[attr.parentKey] = current[attr.idKey]
                child = i == a.length - 1 ? {...t, ...child} : child
                current.children.push(child)
            }
            current = child
            if (typeof p === 'object') {
                Object.entries(p).forEach(([k, v]) => current[k] = v)
            }
            decoFunc && decoFunc(t, current, i)
            current.children?.sort((a: any, b: any) => a[attr.sortKey] - b[attr.sortKey])
        })
    }
    return root.children
}