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
    var username = window.localStorage.getItem("userName");
    if (username) {
        $("#loginusername").text("用户名：" + username);
        //$("#loginphonenum").text("咨询热线：400-000-0000");
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
//判断状态
function GetStateMsg(x) {
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
            msg = "待分总报价";
            break;
        case 12:
            msg = "待总部报价";
            break;
        case 13:
            msg = "待确定报价";
            break;
        case 14:
            msg = "报价驳回";
            break;
    }
    return msg;
};




