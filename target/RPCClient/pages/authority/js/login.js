let LoginPage = (function () {
    let $account;
    let $password;
    let $loginBtn;
    let $registerBtn;

    return {
        init: function () {
            $account = $("#account");
            $password = $("#password");
            $loginBtn = $("#loginBtn");
            $registerBtn = $("#registerBtn");

            $loginBtn.click(function () {
                let account = $account.val();
                let password = EncryptUtil.md5($password.val());
                $.getJSON("/authority/login.action", {
                        account: account,
                        password: password
                    }, function (response) {
                    if (response.status === 200 && response.data === "success") {
                        window.location = "/";
                    } else alert(response.data);
                })
            });

            $registerBtn.click(function () {
                window.location = "/pages/authority/register.html";
            });
        }
    }
})();