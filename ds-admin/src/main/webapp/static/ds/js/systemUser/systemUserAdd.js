layui.use(['form', 'layer', 'easyHttp', 'popup'], function () {
    let $ = layui.jquery;
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    easyHttp.execute({url: '/admin/platform/usable/list', method: 'GET'}, function (resp) {
        let $platformUsernameSelect = $('#platformUsernameSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $platformUsernameSelect.append("<option value='" + p.username + "'>" + p.nickname + "</option>");
        }
        form.render('select', 'systemUserForm');
    });


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        easyHttp.execute({
            url: '/admin/system-user/create',
            method: 'POST',
            data: JSON.stringify(data.field)
        }, function (resp) {
            popup.success('操作成功', function () {
                parent.layer.close(parent.layer.getFrameIndex(window.name));
                parent.layui.table.reload(easyHttp.getQueryVariable('tableId'));
            });
        });
        return false;
    });

});