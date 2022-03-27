window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
    base: rootPath + "module/",
    version: "3.8.10"
}).extend({
    admin: "admin", 	// 框架布局组件
    menu: "menu",		// 数据菜单组件
    frame: "frame", 	// 内容页面组件
    tab: "tab",			// 多选项卡组件
    easyHttp: "easyHttp", // http模块的深度封装
    echarts: "echarts", // 数据图表组件
    echartsTheme: "echartsTheme", // 数据图表主题
    hash: "hash",		// 数据加密组件
    select: "select",	// 下拉多选组件
    drawer: "drawer",	// 抽屉弹层组件
    notice: "notice",	// 消息提示组件
    step: "step",		// 分布表单组件
    tag: "tag",			// 多标签页组件
    popup: "popup",      // 弹层封装
    iconPicker: "iconPicker", // 图表选择
    treetable: "treetable",   // 树状表格
    dtree: "dtree",			// 树结构
    tinymce: "tinymce/tinymce", // 编辑器
    area: "area",			// 省市级联
    count: "count",			// 数字滚动组件
    topBar: "topBar",		// 置顶组件
    button: "button",		// 加载按钮
    design: "design",		// 表单设计
    card: "card",			// 数据卡片组件
    loading: "loading",		// 加载组件
    cropper: "cropper",		// 裁剪组件
    convert: "convert",		// 数据转换
    yaml: "yaml",			// yaml 解析组件
    context: "context",		// 上下文组件
    http: "http",			// ajax请求组件
    theme: "theme",			// 主题转换
    message: "message",     // 通知组件
}).use(['layer', 'theme'], function () {
    layui.theme.changeTheme(window, false);
});

/**
 * 注册全局jwt校验模块
 */
layui.use(['jquery'], function () {
    let $ = layui.jquery;
    // 设置全局的检查事件
    $(document).ajaxSend(function (event, jqXHR, ajaxOptions) {
        if (ajaxOptions.url !== JwtVerify.loginUrl) {
            // 不是登录接口的时候发现jwt有问题就跳转登录
            let ok = JwtVerify.verify();
            if (!ok) {
                JwtOperator.clearJwt();
                JwtVerify.toLoginPage();
                return;
            }
            // 校验正常，不存在token时则加上
            let jwt = JwtOperator.getJwt();
            if (jwt && ajaxOptions.headers !== undefined && ajaxOptions.headers[JwtOperator.headerName] === undefined) {
                jqXHR.setRequestHeader(JwtOperator.headerName, jwt);
            }
        }
    });
});