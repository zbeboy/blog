/**
 * Created by Administrator on 2016/3/30.
 */
$(document).ready(function(){
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'air'
    };
    initZclip();
});

function initZclip(){
    $("#copy").zclip({
        path:'/js/zclip/ZeroClipboard.swf', //记得把ZeroClipboard.swf引入到项目中
        copy:$('#password').val(),
        afterCopy:function(){
            Messenger().post('复制成功');
        }
    });
}