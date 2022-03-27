layui.use(['table', 'form'], function () {
    let table = layui.table;
    let form = layui.form;

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
                    title: '头像', templet: function (d) {
                        let img = d.avatar;
                        if (!img) {
                            img = Constant.memberAvatar;
                        }
                        return '<img class="tb-img-circle" alt=""  src="' + img + '" />';
                    }, align: 'center', width: 90, unresize: true
                },
                {
                    title: '会员名',
                    field: 'username',
                    align: 'center'
                },
                {
                    title: '昵称',
                    field: 'nickname',
                    align: 'center'
                },
                {
                    title: '一级邀请人',
                    field: 'firParent',
                    align: 'center'
                },
                {
                    title: '二级邀请人',
                    field: 'secParent',
                    align: 'center'
                },
                {
                    title: '三级邀请人',
                    field: 'thrParent',
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
     * 搜索按钮点击事件
     */
    form.on('submit(memberQueryFormSubmit)', function (data) {
        Member.onSearch(data);
        return false;
    });


});
