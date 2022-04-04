layui.use(['form', 'layer', 'easyHttp', 'popup'], function () {
    let $ = layui.jquery;
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    easyHttp.execute({url: '/admin/enum/member-rank-type', method: 'GET'}, function (resp) {
        let $memberRankTypeSelect = $('#memberRankTypeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $memberRankTypeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', 'rankParamTable');
    });


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        easyHttp.execute({
            url: '/admin/rank-param/create',
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