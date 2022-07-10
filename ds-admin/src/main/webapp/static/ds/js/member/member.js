layui.use(['table', 'form', 'easyHttp', 'popup'], function () {
    let table = layui.table;
    let form = layui.form;
    let easyHttp = layui.easyHttp;
    let popup = layui.popup;

    /**
     * 页面实体对象
     */
    let Member = {
        tableId: 'memberTable'
    }

    /**
     * 初始化表列
     */
    Member.initCols = function () {
        return [
            [
                {
                    title: '序号',
                    type: 'numbers'
                },
                {
                    title: '头像',
                    minWidth: 40,
                    align: 'center',
                    templet: '#avatarTpl'

                },
                {
                    title: '平台名称',
                    field: 'platformNickname',
                    align: 'center'
                },
                {
                    title: '会员标识',
                    field: 'username',
                    align: 'center'
                },
                {
                    title: '会员名称',
                    field: 'nickname',
                    align: 'center'
                },
                {
                    title: '一级邀请人',
                    field: 'firstInviterNickname',
                    align: 'center'
                },
                {
                    title: '二级邀请人',
                    field: 'secondInviterNickname',
                    align: 'center'
                },
                {
                    title: '三级邀请人',
                    field: 'thirdInviterNickname',
                    align: 'center'
                },
                {
                    title: '身份类型',
                    field: 'identityType',
                    align: 'center'
                },
                {
                    title: '段位类型',
                    field: 'rankType',
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
                    minWidth: 160,
                    align: 'center'
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
     * 搜索操作
     */
    Member.onSearch = function () {
        let query = form.val('memberQueryForm');
        Object.keys(query).forEach(function (key) {
            let value = query[key];
            if (value === '') {
                query[key] = null;
            }
        });
        table.reload(Member.tableId, {
            where: query,
            page: {curr: 1}
        });
    }

    /**
     * 打开账户信息窗口
     */
    Member.openAccountDlg = function (id) {
        layer.open({
            type: 2,
            title: '账户信息',
            shade: 0.3,
            area: ['700px', '600px'],
            content: '/view/member/account?id=' + id,
        });
    };

    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + Member.tableId,
        skin: 'line',
        size: 'lg',
        url: '/admin/member/page',
        method: 'POST',
        page: true,
        contentType: 'application/json',
        request: {pageName: 'page', limitName: 'size'},
        response: {countName: 'total'},
        cols: Member.initCols(),
        toolbar: '#toolbar',
        defaultToolbar: ['filter', 'print', 'exports']
    });

    /**
     * 监听数据行末尾按钮 rowBar
     */
    table.on('tool(' + Member.tableId + ')', function (obj) {
        if (obj.event === 'account') {
            Member.openAccountDlg(obj.data.id);
        }
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(memberQueryFormSubmit)', function (data) {
        Member.onSearch(data);
        return false;
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
            easyHttp.execute({url: '/admin/member/' + path + '/' + data.value, method: 'PUT'}, function (resp) {
                data.elem.checked = !data.elem.checked;
                form.render('checkbox');
                popup.success('操作成功');
                layer.close(index);
            });
        });
    });


});
