layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let ProfitEvent = {
        tableId: 'profitEventTable'
    }

    /**
     * 初始化表列
     */
    ProfitEvent.initCols = function () {
        return [
            [
                {
                    title: '序号',
                    type: 'numbers'
                },
                {
                    title: '分润类型',
                    field: 'profitType',
                    align: 'center'
                },
                {
                    title: '触发会员主键',
                    field: 'triggerMemberId',
                    align: 'center'
                },
                {
                    title: '事件编号',
                    field: 'eventNumber',
                    align: 'center'
                },
                {
                    title: '事件金额',
                    field: 'eventAmount',
                    align: 'center'
                },
                {
                    title: '历史总积分',
                    field: 'createDateTime',
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
    ProfitEvent.onSearch = function () {
        let query = form.val('profitEventQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(ProfitEvent.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + ProfitEvent.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/allot-profit-event/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: ProfitEvent.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(profitEventQueryFormSubmit)', function (data) {
        ProfitEvent.onSearch(data);
        return false;
    });

});
