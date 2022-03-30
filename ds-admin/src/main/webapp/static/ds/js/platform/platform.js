layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let Platform = {
        tableId: 'platformTable'
    }

    /**
     * 初始化表列
     */
    Platform.initCols = function () {
        return [
            [
                {
                    title: '平台标识',
                    field: 'username',
                    align: 'center'
                },
                {
                    title: '平台名称',
                    field: 'nickname',
                    align: 'center'
                },
                {
                    title: '状态',
                    field: 'state',
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
    Platform.onSearch = function () {
        let query = form.val('platformQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(Platform.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + Platform.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/platform/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: Platform.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(platformQueryFormSubmit)', function (data) {
        Platform.onSearch(data);
        return false;
    });


});
