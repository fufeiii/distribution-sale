layui.use(['table', 'form', 'layer', 'popup', 'easyHttp'], function () {
    let $ = layui.jquery;
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;
    let popup = layui.popup;
    let easyHttp = layui.easyHttp;

    // 禁用查询按钮
    if (AdminOperator.isNotAdmin()) {
        let $button = $('button[lay-filter=platformQueryFormSubmit]');
        $button.removeClass('pear-btn-primary');
        $button.addClass('layui-btn-disabled');
    }

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
                    align: 'center',
                    templet: '#stateTpl'
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
        if (AdminOperator.isNotAdmin()) {
            return;
        }
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
     * 弹出添加对话框
     */
    Platform.openAddDlg = function () {
        layer.open({
            type: 2,
            title: '添加用户',
            shade: 0.3,
            area: ['550px', '450px'],
            content: '/view/platform/add?tableId=' + Platform.tableId,
        });
    };


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

    /**
     * 启用禁用点击事件
     */
    form.on('switch(stateBtn)', function (data) {
        let tips = data.elem.checked ? '启用' : '禁用';
        let path = data.elem.checked ? 'enable' : 'disable';
        layer.confirm('确认' + tips, {icon: 3, title: '提示', closeBtn: 0}, function (index) {
            easyHttp.execute({url: '/admin/platform/' + path + '/' + data.value, method: 'PUT'}, function (resp) {
                popup.success('操作成功');
                layer.close(index);
            }, function (resp) {
                popup.failure(resp.msg);
            });
        }, function (index) {
            data.elem.checked = !data.elem.checked;
            form.render('checkbox');
            layer.close(index);
        });
    });

    /**
     * 监听表格上方按钮 toolbar
     */
    table.on('toolbar(' + Platform.tableId + ')', function (obj) {
        if (obj.event === 'add') {
            Platform.openAddDlg();
        }
    });

});
