/**
 * Created by Administrator on 2016/2/19.
 */
$(document).ready(function () {
    $('#username').focus();
    initCaptcha('#captcha');
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
});

/**
 * 初始化验证码区
 */
function initCaptcha(target) {
    var handler = function (captchaObj) {
        // 将验证码加到id为captcha的元素里
        captchaObj.appendTo(target);
    };
    //显示遮罩
    $('#dataRegion').showLoading();
    $.ajax({
        // 获取id，challenge，success（是否启用failback）
        url: '/startCaptcha',
        type: 'get',
        dataType: 'json', // 使用jsonp格式
        success: function (data) {
            // 使用initGeetest接口
            // 参数1：配置参数，与创建Geetest实例时接受的参数一致
            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                product: 'embed', // 产品形式
                offline: !data.success
            }, handler);
            //去除遮罩
            $('#dataRegion').hideLoading();
        }
    });
}

function toRegist() {
    $('#registForm').removeClass('hidden').addClass('show');
    $('#loginForm').removeClass('show').addClass('hidden');
    $('#email').focus();
    initCaptcha('#registCaptcha');
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

/**
 * 点击注册
 */
function regist() {
    validateRegist('#registData');
}

/**
 * 点击登录
 */
function login(){
    validateLogin('#loginData');
}


/**
 * 登录提交时检验
 * @param data
 * @returns {boolean}
 */
function validateLogin(data) {
    var email = $('#username').val();
    var emailRegex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
    if (!emailRegex.test(email)) {
        $('#loginError').text('请填写正确的邮箱！');
        data.username.focus();
        data.username.select();
        return false;
    }

    var password = $('#password').val();
    var passwordRegex = /^(\w){6,20}$/;
    if (!passwordRegex.test(password)) {
        $('#loginError').text('请填写正确的密码！');
        data.password.focus();
        data.password.select();
        return false;
    }

    valideCaptcha(data,'submitLogin()','#loginError');
}

/**
 * 注册时检验
 * @param data
 */
function validateRegist(data){
    if (emailIsRight) {
        var regex = /^(\w){6,20}$/;
        if (!regex.test($('#regist_password').val())) {
            $('#registError').text('密码为6~20个任意字符！');
            $('#checkPassword').removeClass('has-success').addClass('has-error');

        } else {
            if ($('#regist_password').val() != $('#confirm_password').val()) {
                $('#registError').text('请确认密码！');
                $('#checkPassword').removeClass('has-error').addClass('has-success');
                $('#checkConfirmPassword').removeClass('has-success').addClass('has-error');
            } else {
                $('#checkConfirmPassword').removeClass('has-error').addClass('has-success');
                valideCaptcha(data,'submitRegist()','#registError');
            }
        }
    }
}

/**
 * 检验验证码
 */
function valideCaptcha(data,func,errorId) {
    $.ajax({
        type: 'POST',
        url: '/verifyCaptcha',
        data: $(data).serialize(),
        success: function (data) {
            if (data.state) {
                eval(func);
            } else {
                $(errorId).text(data.msg);
            }
        }
    });
}

/**
 * 检验激活
 */
function activation(){
    //显示遮罩
    $('#dataRegion').showLoading();
    $.post('/checkActivate',{
        'username':$('#username').val().trim()
    },function(data){
        //去除遮罩
        $('#dataRegion').hideLoading();
       if(data.state){
           $('#loginData').submit();
       } else {
           $('#loginError').text(data.msg+"，");
           $('#loginError').append($('<a href="javascript:againActivation();"  class="text-primary" >').text( ' 重新激活'));
       }
    });
}

/**
 * 重新激活
 */
function againActivation(){
    //显示遮罩
    $('#dataRegion').showLoading();
    $.post('/againActivation',{
        'username':$('#username').val().trim()
    },function(data){
        //去除遮罩
        $('#dataRegion').hideLoading();
        if(data.state){
            Messenger().post(data.msg);
        } else {
            $('#loginError').text(data.msg);
            Messenger().post(data.msg);
        }
    });
}

/**
 * 提交登录数据
 */
function submitLogin(){
    activation();
}

/**
 * 提交注册数据
 */
function submitRegist(){
    //显示遮罩
    $('#dataRegion').showLoading();
    $.post('/regist', {
            'email': $('#email').val(),
            'regist_password': $('#regist_password').val(),
            'confirm_password': $('#confirm_password').val()
        },
        function (data) {
            //去除遮罩
            $('#dataRegion').hideLoading();
            if (data.state) {
                $('#registError').text('');
                Messenger().post(data.msg);
                setTimeout(function () {
                    window.location.href="/login";
                }, 3000);
            } else {
                $('#registError').text(data.msg);
            }
        }, 'json');
}


