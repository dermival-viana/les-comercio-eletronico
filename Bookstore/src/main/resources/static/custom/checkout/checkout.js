const orderTotal = parseFloat($("#orderTotalId").text().replace(",", ".")).toFixed(2);
let orderTotalTemp = orderTotal;
let exchangeCouponTemp = 0;
let promotionalCouponTemp = 0;

var exchangeCouponView = $("#exchangeCouponViewId");
$("#exchangeCouponCode").val("");
var exchangeCouponSelect = $("#exchangeCouponSelectId").on("change", function (element) {
    let couponValue = parseFloat($(this).children("option:selected").text())
    couponValue = couponValue.toFixed(2);
    if (isNaN(couponValue)) {
        orderTotalTemp = parseFloat(orderTotalTemp) + parseFloat(exchangeCouponTemp);
        $("#orderTotalId").text((orderTotalTemp).toFixed(2));
        $("#orderTotalId2").text((orderTotalTemp).toFixed(2));
        $("#exchangeCouponViewData").text("");
        $("#exchangeCouponCode").val("");
        return;
    }


    $("#exchangeCouponViewData").text($(this).children("option:selected").val());
    $("#exchangeCouponCode").val($(this).children("option:selected").val());

    orderTotalTemp = orderTotalTemp - couponValue;
    exchangeCouponTemp = couponValue;

    $("#orderTotalId").text((orderTotalTemp).toFixed(2));
    $("#orderTotalId2").text((orderTotalTemp).toFixed(2));
});


var promotionalCouponView = $("#promotionalCouponViewId");
var promotionalCouponSelect = $("#promotionalCouponSelectId").on("change", function (element) {
    let pcouponValue = parseFloat($(this).children("option:selected").text())
    pcouponValue = pcouponValue.toFixed(2);
    if (isNaN(pcouponValue)) {
        orderTotalTemp = parseFloat(orderTotalTemp) + parseFloat(promotionalCouponTemp);
        $("#orderTotalId").text((orderTotalTemp).toFixed(2));
        $("#orderTotalId2").text((orderTotalTemp).toFixed(2));
        $("#promotionalCouponViewData").text("");
        $("#promotionalCouponCode").val("");
        return;
    }

    if (parseFloat(pcouponValue) <= parseFloat(orderTotalTemp)) {
        $("#promotionalCouponViewData").text($(this).children("option:selected").val());
        $("#promotionalCouponCode").val($(this).children("option:selected").val());

        orderTotalTemp = orderTotalTemp - pcouponValue;
        promotionalCouponTemp = pcouponValue;

        $("#orderTotalId").text((orderTotalTemp).toFixed(2));
        $("#orderTotalId2").text((orderTotalTemp).toFixed(2));
    } else {
        toastr.warning('Cupom com valor maior que o do pedido.')
    }
});