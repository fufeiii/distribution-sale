layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

    /**
     * 页面实体对象
     */
    let SystemUser = {
        tableId: 'systemUserTable'
    }

    /**
     * 初始化表列
     */
    SystemUser.initCols = function () {
        return [
            [
                {
                    title: '头像', templet: function (d) {
                        let img = d.avatar;
                        if (!img) {
                            img = Constant.defaultAvatar;
                        }
                        return '<img class="tb-img-circle" alt=""  src="' + img + '" />';
                    }, align: 'center', width: 90, unresize: true
                },
                {
                    title: '平台名称',
                    field: 'platformNickname',
                    align: 'center'
                },
                {
                    title: '登录名',
                    field: 'username',
                    align: 'center'
                },
                {
                    title: '用户名',
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
    SystemUser.onSearch = function () {
        let query = form.val('sysUserQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(SystemUser.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + SystemUser.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/system-user/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: SystemUser.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(sysUserQueryFormSubmit)', function (data) {
        SystemUser.onSearch(data);
        return false;
    });


});
