layui.define(['http', 'popup'], function (exports) {
    let http = layui.http;
    let popup = layui.popup;
    let easyHttp = {};

    // 通用ajax
    easyHttp.execute = function (ajaxOptions, successCallback, failureCallback) {
        http.ajax(ajaxOptions)
            .done(function (data) {
                if (data.code === 0) {
                    successCallback && successCallback(data);
                } else {
                    if (failureCallback) {
                        failureCallback(data);
                    } else {
                        popup.failure(data.msg);
                    }
                }
            })
            .fail(function (data) {
                console.log(data)
                popup.failure('服务器错误');
            });
    };

    // 获取url上的参数
    easyHttp.getQueryVariable = function (key) {
        let query = window.location.search.substring(1);
        let kvPairArray = query.split("&");
        for (let i = 0; i < kvPairArray.length; i++) {
            let pair = kvPairArray[i].split("=");
            if (pair[0] === key) {
                return pair[1];
            }
        }
        return '';
    }

    exports('easyHttp', easyHttp);
});