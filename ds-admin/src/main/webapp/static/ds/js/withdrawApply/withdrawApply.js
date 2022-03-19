layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let WithdrawApply = {
        tableId: 'withdrawApplyTable'
    }

    /**
     * 初始化表列
     */
    WithdrawApply.initCols = function () {
        return [
            [
                {
                    title: '会员主键',
                    field: 'memberId',
                    align: 'center'
                },
                {
                    title: '提现编号',
                    field: 'withdrawNumber',
                    align: 'center'
                },
                {
                    title: '提现金额',
                    field: 'withdrawAmount',
                    align: 'center'
                },
                {
                    title: '手续费',
                    field: 'feeAmount',
                    align: 'center'
                },
                {
                    title: '提现描述',
                    field: 'withdrawDesc',
                    align: 'center'
                },
                {
                    title: '审批时间',
                    field: 'approvalTime',
                    align: 'center'
                },
                {
                    title: '审批描述',
                    field: 'approvalDesc',
                    align: 'center'
                },
                {
                    title: '提现状态',
                    field: 'withdrawState',
                    align: 'center'
                },
                {
                    title: '创建日期',
                    field: 'createDateTime',
                    align: 'center'
                }
            ]
        ];
    }

    /**
     * 搜索操作
     */
    WithdrawApply.onSearch = function () {
        let query = form.val('withdrawApplyQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(WithdrawApply.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + WithdrawApply.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/withdraw-apply/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: WithdrawApply.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(withdrawApplyQueryFormSubmit)', function (data) {
        WithdrawApply.onSearch(data);
        return false;
    });


});
