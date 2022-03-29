let Constant = {
    adminAvatar: '/ds/images/avatar.jpg',
    memberAvatar: '/ds/images/avatar.jpg',
}

let JwtOperator = {
    headerName: 'Authorization',
    lsName: 'jwt',
    getJwt: function () {
        return localStorage.getItem(this.lsName);
    },
    setJwt: function (jwt) {
        localStorage.setItem(this.lsName, jwt);
    },
    clearJwt: function () {
        localStorage.removeItem(this.lsName);
    }
};

let JwtVerify = {
    loginPageUrl: '/view/login',
    loginUrl: '/admin/login',
    toLoginPage: function (window) {
        window.location.replace(this.loginPageUrl);
    },
    verify: function () {
        let jwt = JwtOperator.getJwt();
        if (!jwt) {
            console.log('jwt不存在')
            return false;
        }
        let jwtArray = jwt.split(".")
        if (jwtArray.length !== 3) {
            console.log('jwt错误')
            return false;
        }
        return true;
    }
}

