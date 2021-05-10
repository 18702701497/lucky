/**
 * 公共弹出层
 * @param url
 * @param title
 */
function layerOpen(url,title){
    $.ajaxSettings.async = false;
    $.get(url,function (res) {
        layer.open({
            type:1
            ,title:title
            ,area:['800px','450px']
            ,content:res
        });
    });
    $.ajaxSettings.async = true;
}

/**
 * 监听提交事件
 * @param submitType
 * @param type
 */
function mySubmit(filter,type,func) {
    layui.form.on('submit('+filter+')',function(data){
        /**
         * func是方法类型的参数，当方法参数不为空，调用这个方法并传入data.field。意在动态添加表单数值的属性
         * 如这里表单数值中填有资源层级，就给资源添加resourceIds的（tree的节点转成）数组，这样一起传给后端做处理
         * 不同的模块有不同的具体（func）实现，只要将方法作为参数传过来就行，方法的参数写死就是这里的data.field
         */
        if(typeof(func)!='undefined'){
            func(data.field)
        }
        $.ajax({
            url:data.form.action
            ,async:false
            ,type:type
            ,contentType:'application/json;charset=utf-8'
            ,data:JSON.stringify(data.field)
            ,success:function (res) {
                if(res.code==0){
                    layer.closeAll();
                    query();
                }else{
                    layer.alert(res.msg());
                }
            }
        });
        return false;
    });
}

function myDeleted(url){
    $.ajax({
        url:url
        ,async:false
        ,type:'DELETE'
        ,success:function (res) {
            if(res.code==0){
                query();
            }else{
                layer.alert(res.msg);
            }
        }
    });
}
