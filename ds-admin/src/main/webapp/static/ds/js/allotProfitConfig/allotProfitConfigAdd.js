layui.use(['form', 'easyHttp', 'popup'], function () {
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        easyHttp.execute({
            url: '/admin/allot-profit-config/create',
            method: 'POST',
            data: JSON.stringify(data.field)
        }, function (resp) {
            popup.success('添加成功');
            //关闭当前页
            parent.layer.close(parent.layer.getFrameIndex(window.name));
            parent.layui.table.reload(easyHttp.getQueryVariable('tableId'));
        });
        return false;
    });

});