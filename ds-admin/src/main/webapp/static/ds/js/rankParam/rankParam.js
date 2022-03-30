layui.use(['table', 'layer', 'http'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

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
            area: ['500px', '610px'],
            content: '/view/rank-param/edit?tableId=' + RankParam.tableId + '&id=' + id,
        });
    };


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
        if (obj.event === 'edit') {
            RankParam.openEditDlg(obj.data.id);
        }
    });


    /**
     * 搜索按钮点击事件
     */
    form.on('submit(rankParamQueryFormSubmit)', function (data) {
        RankParam.onSearch(data);
        return false;
    });


});
