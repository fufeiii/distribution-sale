layui.use(['table', 'form', 'layer', 'easyHttp', 'popup'], function () {
    let table = layui.table;
    let layer = layui.layer;
    let easyHttp = layui.easyHttp;
    let popup = layui.popup;
    let form = layui.form;

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
            [{
                title: '序号',
                type: 'numbers'
            },
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
                    field: 'beginPoints',
                    align: 'center'
                },
                {
                    title: '结束积分',
                    field: 'endPoints',
                    align: 'center'
                },
                {
                    title: '状态',
                    templet: '#stateTpl'
                },
                {
                    title: '操作',
                    toolbar: '#rowBar',
                    align: 'center',
                    minWidth: 130
                }
            ]
        ];
    }

    /**
     * 弹出添加对话框
     */
    RankParam.openAddDlg = function () {
        layer.open({
            type: 2,
            title: '添加段位配置',
            shade: 0.3,
            area: ['500px', '410px'],
            content: '/view/member-rank-config/add?tableId=' + RankParam.tableId,
        });
    };

    /**
     * 弹出编辑对话框
     */
    RankParam.openEditDlg = function (id) {
        layer.open({
            type: 2,
            title: '编辑段位配置',
            shade: 0.3,
            area: ['500px', '310px'],
            content: '/view/member-rank-config/edit?tableId=' + RankParam.tableId + '&id=' + id,
        });
    };

    /**
     * 移除操作
     */
    RankParam.onRemove = function (id) {
        layer.confirm('确认删除吗', {icon: 3, title: '提示'}, function (index) {
            easyHttp.execute({url: '/admin/member-rank-config/remove/' + id, method: 'DELETE'}, function (resp) {
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
        url: '/admin/member-rank-config/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: RankParam.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: [{title: '刷新', layEvent: 'refresh', icon: 'layui-icon-refresh'}, 'filter', 'print', 'exports']
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
        } else if (obj.event === 'refresh') {
            table.reload(RankParam.tableId);
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
     * 启用禁用点击事件
     */
    form.on('switch(stateBtn)', function (data) {
        let tips = data.elem.checked ? '启用' : '禁用';
        let path = data.elem.checked ? 'enable' : 'disable';
        data.elem.checked = !data.elem.checked;
        form.render('checkbox');
        layer.confirm('确认' + tips, {icon: 3, title: '提示', closeBtn: 0}, function (index) {
            easyHttp.execute({
                url: '/admin/member-rank-config/' + path + '/' + data.value,
                method: 'PUT'
            }, function (resp) {
                data.elem.checked = !data.elem.checked;
                form.render('checkbox');
                popup.success('操作成功');
                layer.close(index);
            });
        });
    });

});
