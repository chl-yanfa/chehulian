(function ($) {
    loduserinfo();
    LoadPermission();
})(jQuery);

function loduserinfo() {
    console.log("localStorage.username:" + localStorage.username)
    if (localStorage.username != "" && localStorage.username != null && localStorage.username != undefined) {
        $("#loginusername").text("用户名：" + localStorage.username)
    } else {
        window.location.href = "login.html"
    }
}
function loginout() {
    localStorage.username = "";
    localStorage.clear();
    console.log("localStorage.username:" + localStorage.username)
    window.location.href = "login.html"
}


function LoadPermission() {
    var per = window.localStorage.getItem("permissions");
    if (per) {
        per = per.split(',');
        $("#backnavper li").each(function (a) {
            for (var t = 0, q = per.length - 1; t < q; t++) {
                if ($(this).html().indexOf(per[t]) >= 0) {
                    $(this).removeClass("displaynone");
                }
            }
        });
    }
}
//规定输入数字的2小数点位数
function VerifyAmount(Amount) {
    var reg = $(Amount).val().match(/\d+\.?\d{0,2}/);
    var txt = '';
    if (reg != null) {
        txt = reg[0];
    }
    $(Amount).val(txt);
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
            msg = "待结算";
            break;
        case 6:
            msg = "部分结算";
            break;
        case 7:
            msg = "已完成";
            break;
        case 8:
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
            msg = "已入库";
            break;
        case 6:
            msg = "已出库";
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
            msg = "已入库";
            break;
        case 6:
            msg = "已出库";
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
            msg = "不接受报价";
            break;
    }
    return msg;
};