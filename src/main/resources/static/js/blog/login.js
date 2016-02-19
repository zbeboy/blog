/**
 * Created by Administrator on 2016/2/19.
 */
$(document).ready(function () {
    $('#registForm').hide();
    $('#username').focus();
});

function toRegist() {
    $('#registForm').show();
    $('#loginForm').hide();
    $('#email').focus();
}
