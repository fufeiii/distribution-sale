layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let Account = {
        tableId: 'accountTable'
    }

    /**
     * 初始化表列
     */
    Account.initCols = function () {
        return [
            [
                {
                    title: '会员主键',
                    field: 'memberId',
                    align: 'center'
                },
                {
                    title: '总金额',
                    field: 'moneyTotal',
                    align: 'center'
                },
                {
                    title: '可用金额',
                    field: 'moneyAvailable',
                    align: 'center'
                },
                {
                    title: '冻结金额',
                    field: 'moneyFrozen',
                    align: 'center'
                },
                {
                    title: '历史总积分',
                    field: 'pointsTotalHistory',
                    align: 'center'
                },
                {
                    title: '总积分',
                    field: 'pointsTotal',
                    align: 'center'
                },
                {
                    title: '可用积分',
                    field: 'pointsAvailable',
                    align: 'center'
                },
                {
                    title: '冻结积分',
                    field: 'pointsFrozen',
                    align: 'center'
                }
            ]
        ];
    }

    /**
     * 搜索操作
     */
    Account.onSearch = function () {
        let query = form.val('accountQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(Account.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + Account.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/account/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: Account.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(accountQueryFormSubmit)', function (data) {
        Account.onSearch(data);
        return false;
    });

});
