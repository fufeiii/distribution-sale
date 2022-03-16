layui.use(['table', 'form', 'layer', 'http', 'popup'], function () {
    let $ = layui.jquery;
    let http = layui.http;
    let form = layui.form;
    let popup = layui.popup;

    //获取详情信息，填充表单
    http.ajax({url: '/admin/profit-param/get/' + http.getQueryVariable('id'), method: 'GET'})
        .done(function (data) {
            if (data.code === 0) {
                form.val(http.getQueryVariable('tableId'), data.data);
            } else {
                popup.failure(data.msg);
            }
        })
        .fail(function (data) {
            console.log(data)
            popup.failure('服务器错误');
        });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        http.ajax({url: '/admin/profit-param/modify', method: 'PUT', data: JSON.stringify(data.field)})
            .done(function (data) {
                if (data.code === 0) {
                    popup.success('编辑成功');
                    //关闭当前页
                    parent.layer.close(parent.layer.getFrameIndex(window.name));
                    parent.layui.table.reload(http.getQueryVariable('tableId'));
                } else {
                    popup.failure(data.msg);
                }
            })
            .fail(function (data) {
                console.log(data)
                popup.failure('服务器错误');
            });
        return false;
    });

});