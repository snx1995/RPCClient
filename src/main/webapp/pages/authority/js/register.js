let RegisterPage = (function () {

    let $registerSubmit;
    let $registerForm;

    let $name;
    let $password;
    let $sex;
    let $phone;
    let $address;
    let $education;
    let $job;
    let $birthday;
    
    return {
        init: function () {
            $registerSubmit = $("#registerSubmit");
            $registerForm = $("#registerForm");

            $name = $("#name");
            $password = $("#password");
            $sex = $("#sex");
            $phone = $("#phone");
            $address = $("#address");
            $education = $("#education");
            $job = $("#job");
            $birthday = $("#birthday");

            initRegisterForm();

            $registerSubmit.click(function () {
                if ($registerForm.valid()) {
                    $.getJSON("/authority/register.action", {
                        name: $name.val(),
                        password: EncryptUtil.md5($password.val()),
                        sex: $sex.val(),
                        phone: $phone.val(),
                        address: $address.val(),
                        education: $education.val(),
                        job: $job.val(),
                        birthday: $birthday.val()
                    }, function (response) {
                        if (response.status === 200 && response.data === "success") {
                            alert("注册成功, 将跳转到登录页面");
                            window.location = "/pages/authority/login.html";
                        } else alert("注册失败, 请重试");
                    })
                }
            });
        }
    };

    function initRegisterForm() {
        $registerForm.validate({
            rules: {
                name: {
                    required: true,
                    maxlength: 20
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 18
                },
                passwordConfirm: {
                    required: true,
                    equalTo: "#password"
                }
            },
            messages: {
                name: {
                    required: "请输入用户名",
                    minlength: "用户名最多由20个汉字或40个英文下的各种字符或数字构成"
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码长度应介于6-18之间",
                    maxlength: "密码长度应介于6-18之间"
                },
                passwordConfirm: {
                    required: "请输入密码",
                    equalTo: "两次密码输入不一致"
                }
            }
        });
    }
})();