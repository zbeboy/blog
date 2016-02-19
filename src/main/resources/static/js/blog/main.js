/**
 * Created by Administrator on 2016/2/15.
 */
$(document).ready(function () {
    var article_id = 0;
    var article_title = '';
    var article_type = '';
    var article_time = '';
    var article_format_time = '';
    var article_author = '';
    var article_comments = '';
    var article_content = '';
    var article_post_num = 0;

    function createPage(pageSize, buttons, total) {
        $(".pagination").jBootstrapPage({
            pageSize: pageSize,
            total: total,
            maxPageButton: buttons,
            onPageClicked: function (obj, pageIndex) {
                sendAjax(pageIndex);
            }
        });
    }

    function showDatas(data){
        $('#articleDatas').empty();
        for (var i = 0; i < data.items.length; i++) {
            article_id = data.items[i].id;
            article_title = data.items[i].blogTitle;
            article_type = data.items[i].blogType;
            article_time = data.items[i].blogCreateTime;
            article_format_time = data.items[i].formatTime;
            article_author = data.items[i].username;
            article_content = data.items[i].blogContent;
            article_post_num++;

            var article_template = "<article class='post post-" + article_post_num + "'>" +
                "<header class='entry-header'>" +
                "<h1 class='entry-title'>" +
                "<p hidden='hidden'>" + article_id + "</p>" +
                "<a href='/article'>" + article_title + "</a>" +
                "</h1>" +
                "<div class='entry-meta'>" +
                "<span class='post-category'><a href='#'>" + article_type + "</a></span>" +
                "<span class='post-date'><a href='#'><time class='entry-date' datetime='" + article_time + "'>" + article_format_time + "</time></a></span>" +
                "<span class='post-author'><a href='#'>" + article_author + "</a></span>" +
                "<span class='comments-link'><a href='#'>" + article_comments + "</a></span>" +
                "</div>" +
                "</header>" +
                "<div class='entry-content clearfix'>" +
                "<p>" + article_content + "</p>" +
                "<div class='read-more cl-effect-14'>" +
                "<a href='#' class='more-link'>Continue reading <span class='meta-nav'>→</span></a>" +
                "</div>" +
                "</div>" +
                " </article>";

            $('#articleDatas').append(article_template);
        }
        $('#articleDatas').append($('<div class="text-center">').append($('<ul class="pagination">')));
    }

    function sendAjax(page){
        $.get("/articleDatas?page=0" ,function (data) {
            showDatas(data);
            createPage(data.pagination.pageSize, data.pagination.buttons, data.pagination.total);
        },"json");
    }

    eval("sendAjax(0)");
});