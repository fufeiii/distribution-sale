layui.use(['table', 'form', 'layer', 'http'], function () {
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
                    type: 'checkbox'
                },
                {
                    title: '用户名',
                    field: 'username',
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
     * 移除操作
     */
    Member.onRemove = function (data) {

    }

    /**
     * 打开新增窗口
     */
    Member.openAddDlg = function (data) {

    }

    /**
     * 打开编辑窗口
     */
    Member.openEditDlg = function (data) {

    }


    /**
     * 表格渲染配置
     */
    table.render({
        elem: '#' + Member.tableId,
        skin: 'line',
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
        if (obj.event === 'add') {
            Member.openAddDlg();
        }
    });

    /**
     * 监听数据行末尾按钮 rowBar
     */
    table.on('tool(' + Member.tableId + ')', function (obj) {
        if (obj.event === 'remove') {
            Member.onRemove(obj);
        } else if (obj.event === 'edit') {
            Member.openEditDlg(obj);
        }
    });


    /**
     * 搜索按钮点击事件
     */
    form.on('submit(memberQueryFormSubmit)', function (data) {
        Member.onSearch(data);
        return false;
    });

})
