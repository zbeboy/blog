/**
 * Created by lenovo on 2016-02-20.
 */
function insertContent() {
    var html = "<div class='form-group'>" +
        "<label for='content' class='col-sm-2 control-label'>Content</label>" +
        "<div class='col-sm-10'>" +
        "<textarea class='content form-control' rows='3' name='content' placeholder='Content'></textarea>" +
        "</div>" +
        "</div>";
    $(html).insertBefore("#insertTag");
}

function article(title, type, content) {
    this.title = title;
    this.type = type;
    this.content = content;
}

function saveArticle() {
    var articleDatas = [];
    var title = $('#title').val();
    var type = $('#type option:selected').val();
    var contentArray = $('.content');
    for (var i = 0; i < contentArray.length; i++) {
        articleDatas.push(new article(title, type, $(contentArray[i]).val()));
    }

    if (title.length <= 0 || title.length > 50) {
        $('#titleError').text('标题为1~50个字符之间！');
        $('#checkTitle').removeClass('has-success').addClass('has-error');
        $('#title').focus();
        return;
    } else {
        $('#titleError').text('');
        $('#checkTitle').removeClass('has-error').addClass('has-success');
    }

    if ($(contentArray[0]).val().length <= 0 || $(contentArray[0]).val().length > 1000) {
        $('#contentError').text('请输入内容！');
        $(contentArray[0]).parent().parent().removeClass('has-success').addClass('has-error');
        $(contentArray[0]).focus();
        return;
    } else {
        $('#contentError').text('');
        $(contentArray[0]).parent().parent().removeClass('has-error').addClass('has-success');
    }
    send(JSON.stringify(articleDatas));
}

function send(data) {
    //显示遮罩
    $('#dataRegion').showLoading();
    $.post('/user/saveArticle', {
        'articledatas': data
    }, function (data) {
        //去除遮罩
        $('#dataRegion').hideLoading();
        if (data.state) {
            window.location.href = "/";
        } else {
            Messenger().post({
                message: data.msg,
                type: 'error',
                showCloseButton: true
            });
        }
    }, 'json');
}

$(document).ready(function () {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
    $("body").forkme("View Forkme","https://github.com/zbeboy/blog");
});