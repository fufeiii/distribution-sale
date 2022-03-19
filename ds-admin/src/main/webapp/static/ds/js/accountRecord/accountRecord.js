layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let AccountRecord = {
        tableId: 'accountRecordTable'
    }

    /**
     * 初始化表列
     */
    AccountRecord.initCols = function () {
        return [
            [
                {
                    title: '会员主键',
                    field: 'memberId',
                    align: 'center'
                },
                {
                    title: '账户主键',
                    field: 'accountId',
                    align: 'center'
                },
                {
                    title: '账户类型',
                    field: 'accountType',
                    align: 'center'
                },
                {
                    title: '变动前金额',
                    field: 'beforeChangeTotal',
                    align: 'center'
                },
                {
                    title: '变动后金额',
                    field: 'afterChangeTotal',
                    align: 'center'
                },
                {
                    title: '变动金额',
                    field: 'changeAmount',
                    align: 'center'
                },
                {
                    title: '变动类型',
                    field: 'changeType',
                    align: 'center'
                },
                {
                    title: '创建时间',
                    field: 'createDateTime',
                    align: 'center'
                },
                {
                    title: '关联变动主键',
                    field: 'changeRecordId',
                    align: 'center'
                }
            ]
        ];
    }

    /**
     * 搜索操作
     */
    AccountRecord.onSearch = function () {
        let query = form.val('accountRecordQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(AccountRecord.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + AccountRecord.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/account-record/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: AccountRecord.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(accountRecordQueryFormSubmit)', function (data) {
        AccountRecord.onSearch(data);
        return false;
    });

});
