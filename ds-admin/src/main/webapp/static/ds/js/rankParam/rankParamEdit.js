layui.use(['form', 'layer', 'easyHttp', 'popup'], function () {
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    //获取详情信息，填充表单
    easyHttp.execute({url: '/admin/rank-param/get/' + easyHttp.getQueryVariable('id'), method: 'GET'}, function (resp) {
        form.val(easyHttp.getQueryVariable('tableId'), resp.data);
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        easyHttp.execute({
            url: '/admin/rank-param/modify',
            method: 'PUT',
            data: JSON.stringify(data.field)
        }, function (resp) {
            popup.success('操作成功');
            //关闭当前页
            parent.layer.close(parent.layer.getFrameIndex(window.name));
            parent.layui.table.reload(easyHttp.getQueryVariable('tableId'));
        });
        return false;
    });

});