layui.use(['form', 'layer', 'easyHttp', 'popup'], function () {
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    //获取详情信息，填充表单
    easyHttp.execute({
        url: '/admin/allot-profit-config/info/' + easyHttp.getQueryVariable('id'),
        method: 'GET'
    }, function (resp) {
        form.val(easyHttp.getQueryVariable('tableId'), resp.data);
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        easyHttp.execute({
            url: '/admin/allot-profit-config/modify',
            method: 'PUT',
            data: JSON.stringify(data.field)
        }, function (resp) {
            popup.success('编辑成功');
            //关闭当前页
            parent.layer.close(parent.layer.getFrameIndex(window.name));
            parent.layui.table.reload(easyHttp.getQueryVariable('tableId'));
        });
        return false;
    });

});