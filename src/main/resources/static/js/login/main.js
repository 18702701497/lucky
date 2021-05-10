/**
 * 打开选项卡，进入相应的模块主页
 * @param url
 * @param name
 * @param id
 */
//判断传入前端id被接收后的长度，根据浏览器不同配置height，width表示在iframe窗口中100%显示，frameborder=0不显示边框，scrolling=no不显示滚动条
//element.tabAdd创建选项卡，menu对应lay-filter，传入三个参数，名字，拼接的窗口格式，id；element.tabChange表示回到对应id的选项卡页面
function showTab(url,name,id){

    let length = $("li[lay-id="+id+"]").length;
    let element=layui.element;
    if(length==0){
        let fullUrl="/"+url;//这是是把
        let height=$(window).height()-185;
        let content='<iframe style="width: 100%;height:'+height+'px" src="'+fullUrl+'" frameborder="0" scrolling="no">'
        element.tabAdd('menu',{
            title:name,
            content:content,
            id:id
        });
    }
    element.tabChange("menu",id);
}