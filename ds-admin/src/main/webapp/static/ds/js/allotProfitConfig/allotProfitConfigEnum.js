layui.use(['form', 'easyHttp'], function () {
    let $ = layui.jquery;
    let easyHttp = layui.easyHttp;
    let form = layui.form;
    let formId = easyHttp.getQueryVariable('formId');

    easyHttp.execute({url: '/admin/enum/account-type', method: 'GET'}, function (resp) {
        let $accountTypeSelect = $('#accountTypeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $accountTypeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });
    easyHttp.execute({url: '/admin/enum/profit-type', method: 'GET'}, function (resp) {
        let $profitTypeSelect = $('#profitTypeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $profitTypeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });


    easyHttp.execute({url: '/admin/enum/calculate-mode', method: 'GET'}, function (resp) {
        let $calculateModeSelect = $('#calculateModeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $calculateModeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });


    easyHttp.execute({url: '/admin/enum/profit-level', method: 'GET'}, function (resp) {
        let $profitLevelSelect = $('#profitLevelSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $profitLevelSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });

    easyHttp.execute({url: '/admin/enum/member-identity-type', method: 'GET'}, function (resp) {
        let $memberIdentityTypeSelect = $('#memberIdentityTypeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $memberIdentityTypeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });

    easyHttp.execute({url: '/admin/enum/member-rank-type', method: 'GET'}, function (resp) {
        let $memberRankTypeSelect = $('#memberRankTypeSelect');
        for (let i = 0; i < resp.data.length; i++) {
            let p = resp.data[i];
            $memberRankTypeSelect.append("<option value='" + p.key + "'>" + p.value + "</option>");
        }
        form.render('select', formId);
    });

});