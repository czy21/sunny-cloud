import type {TableColumn} from "@sunny-framework-js/vue";
import {util} from "@sunny-framework-js/core";

export const fields: TableColumn[] = [
    {
        "prop": "seq",
        "name": "序号",
        "type": "index",
        "fixed": "left",
        "heads": [
            {
                name: "序号"
            }
        ]
    },
    {
        "prop": "name",
        "name": "姓名",
        "editable": true,
        "required": true,
        "heads": [
            {
                name: "姓名",
                style: {
                    width: 200
                }
            }
        ]
    },
    {
        "prop": "age",
        "name": "年龄",
        "heads": ["年龄"],
        "type": "number",
        "editable": true,
        "required": true,
        "min": 1,
        "precision": 0
    },
    {
        "prop": "province",
        "name": "省",
        "heads": [
            "省"
        ],
        "type": "select",
        "dictKey": "DICT_PROVINCE",
        "editable": true
    },
    {
        "prop": "city",
        "name": "市",
        "heads": [
            "市"
        ],
        "type": "select",
        "dictKey": "DICT_CITY",
        "editable": true
    },
    {
        "prop": "district",
        "name": "区",
        "heads": [
            "区"
        ],
        "type": "select",
        "dictKey": "DICT_DISTRICT",
        "editable": true
    },
    {
        "prop": "address",
        "name": "a",
        "heads": [
            {
                name: "a1",
                style: {
                    backgroundColor: "blue"
                }
            },
            "a2",
            {
                name: "地址",
                style: {
                    backgroundColor: "red"
                }
            }
        ],
        "editable": true
    },
    {
        "prop": "entryTime",
        "name": "入职时间",
        "type": "datetime",
        "editable": true,
        "heads": [
            "a1",
            "a2",
            {
                name: "入职时间",
                style: {
                    backgroundColor: "yellow"
                }
            }
        ],
        "width": "250"
    },
    {
        "prop": "hobby",
        "name": "爱好",
        "type": "select",
        "dictKey": "HOBBY",
        "editable": true,
        "heads": [
            "a1",
            "a2",
            {
                name: "爱好",
                style: {
                    backgroundColor: "DarkBlue"
                }
            }
        ],
    },
    {
        "prop": "job",
        "name": "工作",
        "type": "select",
        "dictKey": "JOB",
        "dictPush": {
            "jobCode": "code"
        },
        "dictOnlyOneDefaultSelect": true,
        "editable": true,
        "heads": [
            "a1",
            "a2",
            {
                name: "工作",
                style: {
                    backgroundColor: "JOB"
                }
            }
        ],
    },
    {
        "prop": "m1",
        "name": "1月",
        "heads": [
            "工资",
            "1月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m2",
        "name": "2月",
        "heads": [
            "工资",
            "2月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m3",
        "name": "3月",
        "heads": [
            "工资",
            "3月"
        ],
        "type": "number",
        "editable": "obj.m1 > 100",
        "colTotal": true
    },
    {
        "prop": "m4",
        "name": "4月",
        "heads": [
            "工资",
            "4月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m5",
        "name": "5月",
        "heads": [
            "工资",
            "5月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m6",
        "name": "6月",
        "heads": [
            "工资",
            "6月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m7",
        "name": "7月",
        "heads": [
            "工资",
            "7月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m8",
        "name": "8月",
        "heads": [
            "工资",
            "8月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m9",
        "name": "9月",
        "heads": [
            "工资",
            "9月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m10",
        "name": "10月",
        "heads": [
            "工资",
            "10月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m11",
        "name": "11月",
        "heads": [
            "工资",
            "11月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "m12",
        "name": "12月",
        "heads": [
            "工资",
            "12月"
        ],
        "type": "number",
        "editable": true,
        "colTotal": true
    },
    {
        "prop": "yearTotal",
        "name": "合计",
        "heads": [
            "工资",
            "年合计"
        ],
        "type": "number",
        "colTotal": true,
        "rowTotal": "Array.from({length: 12},(v,i)=>Number(obj[`m${i+1}`]||null)).reduce((a,b)=>a+b)"
    },
    {
        "prop": "action",
        "name": "操作",
        "heads": ["操作"],
        "fixed": "right",
        "width": '200px'
    }
]

export const getColumns = () => {
    fields.forEach((t: any) => {
        if (t.heads && t.required) {
            let leaf = t.heads[t.heads.length - 1]
            if (typeof leaf === 'object' && !leaf.name.startsWith("*")) {
                t.heads[t.heads.length - 1] = {...leaf, ...{name: "*" + leaf.name}}
            }
            if (typeof leaf === 'string' && !leaf.startsWith("*")) {
                t.heads[t.heads.length - 1] = "*" + leaf
            }
        }
    })
    let tree = util.tree.buildByPath(fields, {
        idKey: "name",
        parentKey: "parentName",
        pathsKey: "heads"
    })
    // console.log(tree)
    return tree
}

export const getData = (length = 10) => {
    return Array.from({length: length}).map((t, i) => {
        return {
            "name": "李四" + i,
            "address": i % 4 == 0 ? "上海" : "北京",
            "age": 25,
            "hobby": "b",
            "m1": 100 + i,
            "m2": 100 + (i * 2)
        }
    })
}

export const getDict = () => {
    return {
        "DICT_PROVINCE": [
            {
                "label": "辽宁省",
                "value": "辽宁省",
                "children": [
                    {
                        "label": "沈阳市",
                        "value": "沈阳市",
                        "children": [
                            {
                                "label": "浑南区",
                                "value": "浑南区"
                            }
                        ]
                    }
                ]
            },
            {
                "label": "黑龙江",
                "value": "黑龙江",
                "children": [
                    {
                        "label": "哈尔滨",
                        "value": "哈尔滨",
                        "children": [
                            {
                                "label": "道里区",
                                "value": "道里区"
                            }
                        ]
                    },
                    {
                        "label": "齐齐哈尔",
                        "value": "齐齐哈尔",
                        "children": [
                            {
                                "label": "龙沙区",
                                "value": "龙沙区"
                            },
                            {
                                "label": "建华区",
                                "value": "建华区"
                            }
                        ]
                    }
                ]
            }
        ],
        "HOBBY": [
            {"label": "唱歌", "value": "music"},
            {"label": "跳舞", "value": "dance"}
        ],
        "JOB": [
            {"label": "IT", "value": "IT", "extra": {"code": "1"}}
        ]
    }
}

export const getSubTotal = () => {
    return [
        {
            "key": "一月<105",
            "groupBy": (item: any, data?: any[]) => item.m1 < 105
        },
        {
            "key": "地址",
            "groupBy": (item: any, data?: any[]) => item.address,
            "byValue": true
        }
    ]
}