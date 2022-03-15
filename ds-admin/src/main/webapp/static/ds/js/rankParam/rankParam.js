layui.use(['table', 'form', 'layer', 'http', 'popup'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let $ = layui.$;
    let http = layui.http;
    let popup = layui.popup;

    /**
     * 页面实体对象
     */
    let RankParam = {
        tableId: 'rankParamTable'
    }

    /**
     * 初始化表列
     */
    RankParam.initCols = function () {
        return [
            [
                {
                    title: '用户段位类型',
                    field: 'memberRankType',
                    align: 'center'
                },
                {
                    title: '开始积分',
                    field: 'beginIntegral',
                    align: 'center'
                },
                {
                    title: '结束积分',
                    field: 'endIntegral',
                    align: 'center'
                },
                {
                    title: '状态',
                    field: 'state',
                    align: 'center'
                },
                {
                    title: '操作',
                    toolbar: '#rowBar',
                    align: 'center',
                    width: 130
                }
            ]
        ];
    }


    /**
     * 搜索操作
     */
    RankParam.onSearch = function () {
        let query = form.val('rankParamQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(RankParam.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 弹出添加对话框
     */
    RankParam.openAddDlg = function () {
        layer.open({
            type: 2,
            title: '添加段位参数',
            shade: 0.3,
            area: ['500px', '610px'],
            content: '/admin/rank-param/add?tableId=' + RankParam.tableId,
        });
    };

    /**
     * 弹出编辑对话框
     */
    RankParam.openEditDlg = function (id) {
        layer.open({
            type: 2,
            title: '编辑段位参数',
            shade: 0.3,
            area: ['500px', '610px'],
            content: '/admin/rank-param/edit?tableId=' + RankParam.tableId + '&id=' + id,
        });
    };

    /**
     * 移除操作
     */
    RankParam.onRemove = function (id) {
        layer.confirm('确认删除吗', {icon: 3, title: '提示'}, function (index) {
            http.ajax({url: '/admin/rank-param/remove/' + id, method: 'DELETE'})
                .done(function (data) {
                    if (data.code === 0) {
                        popup.success('操作成功');
                        RankParam.onSearch();
                    } else {
                        popup.failure(data.msg);
                    }
                })
                .fail(function (data) {
                    console.log(data)
                    popup.failure('服务器错误');
                });
            layer.close(index);
        });

    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + RankParam.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/rank-param/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: RankParam.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 监听表格上方按钮 toolbar
     */
    table.on('toolbar(' + RankParam.tableId + ')', function (obj) {
        if (obj.event === 'add') {
            RankParam.openAddDlg();
        }
    });

    /**
     * 监听数据行末尾按钮 rowBar
     */
    table.on('tool(' + RankParam.tableId + ')', function (obj) {
        if (obj.event === 'remove') {
            RankParam.onRemove(obj.data.id);
        } else if (obj.event === 'edit') {
            RankParam.openEditDlg(obj.data.id);
        }
    });


    /**
     * 搜索按钮点击事件
     */
    form.on('submit(*)', function (data) {
        RankParam.onSearch(data);
        return false;
    });


});
