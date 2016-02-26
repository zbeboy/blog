/**
 * Created by Administrator on 2016/2/19.
 */


$(document).ready(function () {
    $('#registForm').hide();
    $('#username').focus();

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
});

function toRegist() {
    $('#registForm').show();
    $('#loginForm').hide();
    $('#email').focus();
}

var emailIsRight = false;

function checkEmail(obj) {

    if ($(obj).val().trim().length <= 0) {
        $('#registError').text('请填写邮箱！');
        emailIsRight = false;
        $('#checkEmail').removeClass('has-success').addClass('has-error');
        return;
    }

    var regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
    if (!regex.test($(obj).val())) {
        $('#registError').text('邮箱格式错误！');
        $('#checkEmail').removeClass('has-success').addClass('has-error');
        emailIsRight = false;
        return;
    } else {
        $.post('/checkEmail', {
            'email': $(obj).val()
        }, function (data) {
            if (data.state) {
                $('#checkEmail').removeClass('has-error').addClass('has-success');
                $('#registError').text('');
                emailIsRight = true;
            } else {
                $('#checkEmail').removeClass('has-success').addClass('has-error');
                $('#registError').text(data.msg);
                emailIsRight = false;
            }
        }, 'json');
    }
}

var passwordIsRight = false;
function checkPassword(obj) {
    var regex = /^(\w){6,20}$/;
    if (!regex.test($(obj).val())) {
        $('#registError').text('密码为6~20个任意字符！');
        $('#checkPassword').removeClass('has-success').addClass('has-error');
        passwordIsRight = false;
    } else {
        $('#registError').text('');
        $('#checkPassword').removeClass('has-error').addClass('has-success');
        passwordIsRight = true;
    }
}

function checkConfirmPassword(obj) {
    if (!passwordIsRight) {
        return;
    } else {
        if ($('#regist_password').val() != $(obj).val()) {
            $('#registError').text('请确认密码！');
            $('#checkConfirmPassword').removeClass('has-success').addClass('has-error');
        } else {
            $('#registError').text('');
            $('#checkConfirmPassword').removeClass('has-error').addClass('has-success');
        }
    }
}

function regist() {

    if (emailIsRight) {
        var regex = /^(\w){6,20}$/;
        if (!regex.test($('#regist_password').val())) {
            $('#registError').text('密码为6~20个任意字符！');
            $('#checkPassword').removeClass('has-success').addClass('has-error');
            return;
        } else {
            if ($('#regist_password').val() != $('#confirm_password').val()) {
                $('#registError').text('请确认密码！');
                $('#checkPassword').removeClass('has-error').addClass('has-success');
                $('#checkConfirmPassword').removeClass('has-success').addClass('has-error');
            } else {
                $('#checkConfirmPassword').removeClass('has-error').addClass('has-success');
                $.post('/regist', {
                        'email': $('#email').val(),
                        'regist_password': $('#regist_password').val(),
                        'confirm_password': $('#confirm_password').val()
                    },
                    function (data) {
                        if (data.state) {
                            $('#registError').text('');
                            Messenger().post(data.msg);
                            setTimeout(function () {
                                window.location.reload(true);
                            }, 3000);
                        } else {
                            $('#registError').text(data.msg);
                        }
                    }, 'json');
            }
        }
    }
}

function validate(data) {
    var email = data.username.value;
    var emailRegex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
    if (!emailRegex.test(email)) {
        $('#loginError').text('请填写正确的邮箱！');
        data.username.focus();
        data.username.select();
        return false;
    }

    var password = data.password.value;
    var passwordRegex = /^(\w){6,20}$/;
    if (!passwordRegex.test(password)) {
        $('#loginError').text('请填写密码！');
        data.password.focus();
        data.password.select();
        return false;
    }

    return true;
}


