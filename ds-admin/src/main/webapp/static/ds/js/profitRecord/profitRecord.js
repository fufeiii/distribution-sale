layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let ProfitRecord = {
        tableId: 'profitRecordTable'
    }

    /**
     * 初始化表列
     */
    ProfitRecord.initCols = function () {
        return [
            [
                {
                    title: '事件主键',
                    field: 'profitEventId',
                    align: 'center'
                },
                {
                    title: '账户类型',
                    field: 'accountType',
                    align: 'center'
                },
                {
                    title: '获利会员主键',
                    field: 'impactMemberId',
                    align: 'center'
                },
                {
                    title: '获利数',
                    field: 'incomeCount',
                    align: 'center'
                },
                {
                    title: '备注',
                    field: 'memo',
                    align: 'center'
                }
            ]
        ];
    }

    /**
     * 搜索操作
     */
    ProfitRecord.onSearch = function () {
        let query = form.val('profitRecordQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(ProfitRecord.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + ProfitRecord.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/profit-record/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: ProfitRecord.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(profitRecordQueryFormSubmit)', function (data) {
        ProfitRecord.onSearch(data);
        return false;
    });

});
