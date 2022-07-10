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
                    title: '事件编号',
                    field: 'eventNumber',
                    align: 'center',
                    minWidth: 280
                },
                {
                    title: '分润类型',
                    field: 'profitType',
                    align: 'center'
                },
                {
                    title: '触发会员',
                    field: 'triggerMember',
                    align: 'center'
                },
                {
                    title: '事件金额',
                    field: 'eventAmount',
                    align: 'center'
                },
                {
                    title: '通知状态',
                    field: 'notifyState',
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
                },
                {
                    title: '操作',
                    toolbar: '#rowBar',
                    align: 'center',
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
     * 打开账户信息窗口
     */
    ProfitEvent.openInfoDlg = function (id) {
        layer.open({
            type: 2,
            title: '事件信息',
            shade: 0.3,
            area: ['700px', '800px'],
            content: '/view/allot-profit-event/info?id=' + id,
        });
    };

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

    /**
     * 监听数据行末尾按钮 rowBar
     */
    table.on('tool(' + ProfitEvent.tableId + ')', function (obj) {
        if (obj.event === 'info') {
            ProfitEvent.openInfoDlg(obj.data.id);
        }
    });

});
