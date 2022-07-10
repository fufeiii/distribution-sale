layui.use(['form', 'table', 'easyHttp', 'popup'], function () {
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let popup = layui.popup;
    let table = layui.table;

    // 查询会员信息
    easyHttp.execute({
        url: '/admin/allot-profit-event/info/' + easyHttp.getQueryVariable('id'),
        method: 'GET'
    }, function (resp) {
        form.val('allotProfitEventInfoForm', resp.data);
    }, function (resp) {
        popup.failure(resp.msg);
    });


    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#profitIncomeRecordTable',
        skin: 'line',
        size: 'lg',
        url: '/admin/allot-profit-event/profit-income-record/list/' + easyHttp.getQueryVariable('id'),
        method: 'GET',
        page: false,
        defaultToolbar: ['filter', 'print', 'exports'],
        cols: [
            [
                {
                    title: '序号',
                    type: 'numbers'
                },
                {
                    title: '账户类型',
                    field: 'accountType',
                    align: 'center',
                },
                {
                    title: '获利会员',
                    field: 'impactMember',
                    align: 'center'
                },
                {
                    title: '收入数量(元/个)',
                    field: 'incomeCount',
                    align: 'center'
                },
                {
                    title: '创建日期',
                    field: 'createDateTime',
                    align: 'center'
                },
                {
                    title: '备注',
                    field: 'memo',
                    align: 'center'
                }
            ]
        ]
    });

});