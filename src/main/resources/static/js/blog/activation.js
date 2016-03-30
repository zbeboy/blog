/**
 * Created by Administrator on 2016/3/30.
 */
    var t = 5;//设定跳转的时间
    function refer() {
        if (t == 0) {
            location = "/login"; //#设定跳转的链接地址
        }
        document.getElementById('show').innerHTML = "" + t + "秒后跳转登录页"; // 显示倒计时
        t--; // 计数器递减
    }
    setInterval('refer()', 1000); //启动1秒定时

