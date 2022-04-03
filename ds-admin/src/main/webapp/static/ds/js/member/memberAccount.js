layui.use(['form', 'easyHttp', 'popup'], function () {
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;

    // 查询会员信息
    easyHttp.execute({url: '/admin/member/account/' + easyHttp.getQueryVariable('id'), method: 'GET'}, function (resp) {
        form.val('memberAccountForm', resp.data);
    }, function (resp) {
        popup.failure(resp.msg);
    });

});