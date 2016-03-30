/**
 * Created by Administrator on 2016/3/30.
 */
$(document).ready(function(){
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
});

function inputEmail(obj){
    if($(obj).val().trim().length>0){
        $('#resetButton').attr('disabled',false);
    } else {
        $('#resetButton').attr('disabled',true);
    }
}

function resetPassword(){

    var email = $('#email').val().trim();

    if (email.length <= 0) {
        Messenger().post({
            message: '请填写邮箱!',
            type: 'error',
            showCloseButton: true
        });
        return;
    }

    var regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
    if (!regex.test(email)) {
        Messenger().post({
            message: '邮箱格式错误!',
            type: 'error',
            showCloseButton: true
        });
    } else {
        $.post('/checkEmail', {
            'email': email
        }, function (data) {
            if (data.state) {
                Messenger().post({
                    message: data.msg,
                    type: 'error',
                    showCloseButton: true
                });
            } else {
                submitUsername();
            }
        }, 'json');
    }
}

function submitUsername(){
    $.post('/resetPassword',{
        'username':$('#email').val().trim()
    },function(data){
        if(data.state){
            Messenger().post(data.msg);
        } else {
            Messenger().post({
                message: data.msg,
                type: 'error',
                showCloseButton: true
            });
        }
    })
}