(function ($) {
    //debugger;
    loduserinfo();
    //加载登录城市
    LoadLoginCitys();
    //加载用户权限
    LoadPermission();
})(jQuery);

function loduserinfo()
{
    var username = window.localStorage.getItem("userName");//phone
    var phone = window.localStorage.getItem("phone");//
    if (username) {
        $("#loginusername").text("用户名：" + username);
    }
    else {
        if (phone) {
            $("#loginusername").text("用户名：" + phone);
        }
    }
    if (window.localStorage.getItem("locationCity") && window.localStorage.getItem("locationCity")!="请选择") {
        $(".firstcity").text(window.localStorage.getItem("locationCity"));
        if (document.getElementById("onlycity")) {
            $("#onlycity").text(window.localStorage.getItem("locationCity"));
        }
        if (document.getElementById("onlycityspan")) {
            $("#onlycityspan").text(window.localStorage.getItem("locationCity"));
        }
    }
    else {
        window.localStorage.setItem("locationCity", "北京");
        $(".firstcity").text(window.localStorage.getItem("locationCity"));
        $("#onlycity").text(window.localStorage.getItem("locationCity"));
        $("#onlycityspan").text(window.localStorage.getItem("locationCity"));
    }
    if (window.localStorage.getItem("locationCityId")) {
        $(".firstcityid").text(window.localStorage.getItem("locationCityId"));
    }
    else {
        window.localStorage.setItem("locationCityId", 2);
        $(".firstcityid").text(window.localStorage.getItem("locationCityId"));
    }
    //console.log("localStorage.locationCity:" + window.localStorage.getItem("locationCity"));
    //console.log("localStorage.locationCityId:" + window.localStorage.getItem("locationCityId"));
}
function loginout()
{
    var cs = window.localStorage.getItem("locationCity");
    var csid = window.localStorage.getItem("locationCityId");
    window.localStorage.setItem("userName", "");
    localStorage.clear();
    window.localStorage.setItem("locationCity", cs);
    window.localStorage.setItem("locationCityId", csid);
    console.log(window.localStorage.getItem("userName"));
    window.location.href = "index.html"
}

function LoadLoginCitys() {
    $.ajax({
        type: "get",
        url: href + 'area',
        data: { carScrapType: 0 },
        dataType: "json",
        success: function (obj) {
            if (obj.msg == "success") {
                $(".topcity").find(".dropdown-menu").find("li").remove();
                for (var i = 0; i < obj.data.length; i++) {
                    var name = "'" + obj.data[i].areasName + "'";
                    $(".topcity").find(".dropdown-menu").append('<li><a href="#" onclick="clicka(' + name + ',' + obj.data[i].id + ')" >' + obj.data[i].areasName + '</a></li>');
                }
            }
        }
    });
}
//切换城市
function clicka(a, b) {
    $(".firstcity").text(a);
    $(".firstcityid").val(b);
    if (document.getElementById("onlycity")) {
        $("#onlycity").text(a);
    }
    if (document.getElementById("onlycityspan")) {
        $("#onlycityspan").text(a);
    }
    window.localStorage.setItem("locationCity", a);
    window.localStorage.setItem("locationCityId", b);
}

function LoadPermission() {
    var per = window.localStorage.getItem("businessType");
    if (per) {
        if (per.indexOf(",1") >= 0) {
            $(".kzper1").removeAttr("style");
        }
        if (per.indexOf(",2") >= 0) {
            $(".kzper2").removeAttr("style");
        }
        if (per.indexOf(",null") >= 0)
        {
            $(".kzper1").removeAttr("style");
            $(".kzper2").removeAttr("style");
        }
    }
}
//获取整车状态
function GetCarStateMsg(x) {
    var msg = x;
    switch (x) {
        case -1:
            msg = "已取消";
            break;
        case 1:
            msg = "待接单";
            break;
        case 2:
            msg = "待取车";
            break;
        case 3:
            msg = "待入场";
            break;
        case 4:
            msg = "待报废";
            break;
        case 5:
            msg = "待出具报废证明";
            break;
        case 6:
            msg = "待结算";
            break;
        case 7:
            msg = "已完成";
            break;
        case 8:
            msg = "异常";
            break;
        case 11:
            msg = "待报价";
            break;
        case 12:
            msg = "待报价";
            break;
        case 13:
            msg = "已报价";
            break;
        case 14:
            msg = "不接受报价";
            break;
    }
    return msg;
};

//获取旧件订单状态
function GetOldStateMsg(x) {
    var msg = x;
    switch (x) {
        case -1:
            msg = "已取消";
            break;
        case 1:
            msg = "待接单";
            break;
        case 2:
            msg = "待回收";
            break;
        case 3:
            msg = "待入库";
            break;
        case 4:
            msg = "无法接收";
            break;
        case 5:
            msg = "待结算";
            break;
        case 6:
            msg = "待结算";
            break;
        case 7:
            msg = "部分结算";
            break;
        case 8:
            msg = "已完成";
            break;
        case 9:
            msg = "异常";
            break;
        case 11:
            msg = "待报价";
            break;
        case 12:
            msg = "待报价";
            break;
        case 13:
            msg = "已报价";
            break;
        case 14:
            msg = "不接受报价";
            break;
    }
    return msg;
};

//获取旧件状态
function GetOldPartStateMsg(x) {
    var msg = x;
    switch (x) {
        case -1:
            msg = "已取消";
            break;
        case 1:
            msg = "待接单";
            break;
        case 2:
            msg = "待回收";
            break;
        case 3:
            msg = "待入库";
            break;
        case 4:
            msg = "无法接收";
            break;
        case 5:
            msg = "待结算";
            break;
        case 6:
            msg = "待结算";
            break;
        case 7:
            msg = "部分结算";
            break;
        case 8:
            msg = "已完成";
            break;
        case 9:
            msg = "异常";
            break;
        case 11:
            msg = "待报价";
            break;
        case 12:
            msg = "待报价";
            break;
        case 13:
            msg = "已报价";
            break;
        case 14:
            msg = "不接受报价";
            break;
    }
    return msg;
};

function DateTimeFormat(now, mask) {
    var d = new Date(now);
    var zeroize = function (value, length) {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length) ; i++) {
            zeros += '0';
        }
        return zeros + value;
    };

    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
                // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};