layui.use(['table', 'layer', 'easyHttp', 'popup'], function () {
    let table = layui.table;
    let layer = layui.layer;
    let easyHttp = layui.easyHttp;
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
                    title: '平台名称',
                    field: 'platformNickname',
                    align: 'center'
                },
                {
                    title: '会员段位类型',
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
                    title: '操作',
                    toolbar: '#rowBar',
                    align: 'center',
                    width: 130
                }
            ]
        ];
    }


    /**
     * 弹出编辑对话框
     */
    RankParam.openEditDlg = function (id) {
        layer.open({
            type: 2,
            title: '编辑段位参数',
            shade: 0.3,
            area: ['500px', '310px'],
            content: '/view/rank-param/edit?tableId=' + RankParam.tableId + '&id=' + id,
        });
    };

    /**
     * 移除操作
     */
    RankParam.onRemove = function (id) {
        layer.confirm('确认删除吗', {icon: 3, title: '提示'}, function (index) {
            easyHttp.execute({url: '/admin/rank-param/remove/' + id, method: 'DELETE'}, function (resp) {
                popup.success('操作成功');
                table.reload(RankParam.tableId);
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
        if (obj.event === 'remove') {
            RankParam.onRemove(obj.data.id);
        } else if (obj.event === 'edit') {
            RankParam.openEditDlg(obj.data.id);
        }
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


});
