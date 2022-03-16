layui.use(['table', 'form', 'layer', 'http', 'popup'], function () {
    let table = layui.table;
    let form = layui.form;
    let layer = layui.layer;

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
                    title: '头像',
                    field: 'avatar',
                    align: 'center'
                },
                {
                    title: '用户名',
                    field: 'username',
                    align: 'center'
                },
                {
                    title: '昵称',
                    field: 'nickname',
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
     * 新增
     */
    Member.openDetailDlg = function () {
        layer.open({
            type: 2,
            title: '会员详情',
            shade: 0.3,
            area: ['500px', '610px'],
            content: '/admin/member/detail?tableId=' + Member.tableId,
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
     * 监听表格上方按钮 toolbar
     */
    table.on('toolbar(' + Member.tableId + ')', function (obj) {
        if (obj.event === 'detail') {
            Member.openDetailDlg();
        }
    });

    /**
     * 搜索按钮点击事件
     */
    form.on('submit(memberQueryFormSubmit)', function (data) {
        Member.onSearch(data);
        return false;
    });


});
