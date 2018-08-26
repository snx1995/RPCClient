let AuthorityManage = (function () {
    let user;

    return {
        init: function () {
            getUser();
        },
        getCurrentUser: function () {
            return user;
        }
    };

    /**
     * 获取用户信息的方法
     * @param id 如果id不为null, 优先按id查询
     * @param name 如果id为null, 则按name查询,
     *     如果name也为null, 则返回当前登录用户
     */
    function getUser(id, name) {
        $.getJSON("/authority/getUserInfo.action", {
            id: id,
            name: name
        }, function (response) {
            user = response.data;
        })
    }
})();