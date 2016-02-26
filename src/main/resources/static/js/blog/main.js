/**
 * Created by Administrator on 2016/2/15.
 */
var article_id = 0;
var article_title = '';
var article_type = '';
var article_type_id = 0;
var article_time = '';
var article_format_time = '';
var article_archives_id = 0;
var article_author = '';
var article_comments = '';
var article_content = '';
var article_post_num = 0;

var archviesId = 0;
var typeId = 0;

function createPage(data) {
    $(".pagination").jBootstrapPage({
        pageSize : data.pagination.pageSize,
        total : data.pagination.totalDatas,
        maxPageButton:data.pagination.buttons,
        onPageClicked: function(obj, pageIndex) {
            nextPage(pageIndex,archviesId,typeId);
        }
    });
}

function showDatas(data) {
    $('#articleDatas').empty();
    archviesId = data.pagination.archviesId;
    typeId = data.pagination.typeId;
    for (var i = 0; i < data.items.length; i++) {
        article_id = data.items[i].id;
        article_title = data.items[i].blogTitle;
        article_type = data.items[i].blogType;
        article_type_id = data.items[i].blogTypeId;
        article_time = data.items[i].blogCreateTime;
        article_format_time = data.items[i].formatTime;
        article_archives_id = data.items[i].archivesId;
        article_author = data.items[i].username;
        article_content = data.items[i].blogContent;
        article_post_num++;

        var article_template = "<article class='post post-" + article_post_num + "'>" +
            "<header class='entry-header'>" +
            "<h1 class='entry-title'>" +
            "<a href='/article?id=" + article_id + "'>" + article_title + "</a>" +
            "</h1>" +
            "<div class='entry-meta'>" +
            "<span class='post-category'><a href='javascript:;' onclick='sendAjax(0,0," + article_type_id + ");' >" + article_type + "</a></span>" +
            "<span class='post-date'><a href='javascript:;' onclick='sendAjax(0," + article_archives_id + ",0)'><time class='entry-date' datetime='" + article_time + "'>" + article_format_time + "</time></a></span>" +
            "<span class='post-author'><a href='#'>" + article_author + "</a></span>" +
            "<span class='comments-link'><a href='#'>" + article_comments + "</a></span>" +
            "</div>" +
            "</header>" +
            "<div class='entry-content clearfix'>" +
            "<p>" + article_content + "</p>" +
            "<div class='read-more cl-effect-14'>" +
            "<a href='/article?id=" + article_id + "' class='more-link'>Continue reading <span class='meta-nav'>→</span></a>" +
            "</div>" +
            "</div>" +
            " </article>";

        $('#articleDatas').append(article_template);
    }
}

function sendAjax(page, archviesId, typeId) {
    $.post('/articleDatas', {
        'page': page,
        'archviesId': archviesId,
        'typeId': typeId
    }, function (data) {
        if(data.items.length>0){
            showDatas(data);
            createPage(data);
        }
    }, 'json');
}

function nextPage(page,archviesId, typeId){
    $.post('/articleDatas', {
        'page': page,
        'archviesId': archviesId,
        'typeId': typeId
    }, function (data) {
        if(data.items.length>0){
            showDatas(data);
        }
    }, 'json');
}

$(document).ready(function () {
    sendAjax(0, archviesId, typeId);
});