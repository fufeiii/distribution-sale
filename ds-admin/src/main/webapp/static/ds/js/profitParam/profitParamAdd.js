layui.use(['table', 'form', 'layer', 'http', 'popup'], function () {
    let $ = layui.jquery;
    let http = layui.http;
    let form = layui.form;
    let popup = layui.popup;

    // // 监听分润类型（邀请分润只能选择固定分润）
    // form.on('select(profitType)', function(data){
    //     let isInv = data.value === 'INVITE';
    //     if (isInv) {
    //         form.val("distProfitParamForm", {
    //
    //         });
    //     }
    // });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let calculateMode = data.field.calculateMode;
        let profitType = data.field.profitType;
        if (calculateMode === 'PERCENTAGE' && profitType !== 'TRADE') {
            popup.warming("计算模式 [百分比] 仅支持 分润类型为 [商品交易] 的场景");
            return false;
        }
        console.log(data.field)
        http.ajax({url: '/admin/profit-param/add', method: 'POST', data: JSON.stringify(data.field)})
            .done(function (data) {
                if (data.code === 0) {
                    popup.success('添加成功');
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