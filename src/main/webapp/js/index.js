let IndexPage = (function () {
    let $user;
    
    return {
        init: function () {
            $user = $("#user");
            getCurrentUser();
        }
    };
    
    function getCurrentUser() {
        $.getJSON("/authority/getUserInfo.action", function (response) {
            let user = response.data;
            if (user.id) $user.html("<i class=\"fa fa-user\"></i><a href='/pages/authority/self-center.html'>" +
                response.data.name + "</a>");
            else  $user.html("<i class=\"fa fa-user\"></i><a href='/pages/authority/login.html'>" +
                response.data.name + "</a>");
        })
    }
})();