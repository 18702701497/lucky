var table = layui.table

// 第一个实例
var tableIns = table.render({
    elem: '#customerList'
    , url: '/customer/list'
    , page: true//开启分页
    , parseData: function (res) { //res 即为原始返回的数据
        return {
            "code": res.code, //解析接口状态
            "msg": res.msg, //解析提示文本
            "count": res.data.count, //解析数据长度
            "data": res.data.records //解析数据列表
        };
    }
    , cols: [[//表头
        {field: 'realName', title: '真实姓名'}
        , {field: 'sex', title: '性别'}
        , {field: 'age', title: '年龄'}
        , {field: 'phone', title: '手机号码'}
        , {field: 'address', title: '地址'}
        , {field: 'createTime', title: '创建时间'}
        , {title: '操作', toolbar: '#barDemo'}//toolbar绑定barDemo工具条
    ]]
});

/**
 * 按条件查询
 */
function query() {
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            realName:$("#realName").val()
            ,phone: $("#phone").val()
        }
        ,page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}
function toAdd() {//新增方法绑定在List页面，点击新增按钮就会加载，无需在add.html页面再注入.js
    layerOpen("/customer/toAdd",'新增客户')
    layui.form.render();//更新渲染，默认全部
    mySubmit("addSubmit","POST")
}


//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

    let customerId=data.customerId;

    if(layEvent === 'detail'){ //查看
        layerOpen("/customer/toDetail/"+customerId,'客户详情')
    } else if(layEvent === 'del'){ //删除
        layer.confirm('确定删除该客户吗？', function(index){
            //向服务端发送删除指令
            layer.close(index);
            myDeleted("/customer/"+customerId)
        });
    } else if(layEvent === 'edit'){ //编辑
        layerOpen("/customer/toUpdate/"+customerId,'修改客户')
        layui.form.render();//layui的form表单渲染（默认全部）
        mySubmit("updateSubmit","PUT")
    }
});
