<%@ page import="edu.nju.onlinestock.model.Orders" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.PayMethod" %>
<%@ page import="util.OrderConditionEnum" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Hostel Info</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="margin-left: -50px">Ticket World</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="main.action">首页</a></li>
                <li><a href="main.action">预订</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="main.action">登录/注册</a></li>
                <li><a href="hostelRegister_hostelRegister.action">我要开场馆</a></li>
            </ul>

        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="navbar navbar-default navbar-fixed" style="width: 150px;height: 620px;top: 50px">
    <ul class="nav nav-pills nav-stacked" style="margin-top: 50px;">
        <li role="presentation"><a href="manager_getApply.action"><h5 style="padding-left: 20px">审批申请</h5></a>
        </li>
        <li role="presentation"><a href="manager_getSettle.action"><h5 style="padding-left: 20px">场馆结算</h5></a></li>
        <li role="presentation" class="active"><a href="worldSta.action"><h5 style="padding-left: 20px">统计信息</h5></a>
        </li>
    </ul>
</div>
<%
    double totalProfit = (double) request.getAttribute("totalProfit");
    double hostelProfit = (double) request.getAttribute("hostelProfit");
    int vipCount = (int) request.getAttribute("vipCount");
    HashMap<String, Integer> checkNums = (HashMap<String, Integer>) request.getAttribute("checkNums");
    String jsonObject= JSONObject.toJSONString(checkNums);
%>
<div style="position: absolute;top:80px;left:160px;width: 1000px;height: 600px;">

    <div class="row">
        <div class="col-md-4" style="padding-left: 30px">
            <div style="background: #337AB7;margin-right: 0.8em;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);transition: 0.5s all;border-radius: 20px">
                <div class="col-md-8" style="padding-left: 30px;">
                    <h3 style="color: #fff;font-size: 2.5em;font-family: 'Carrois Gothic', sans-serif;">¥<%=totalProfit%></h3>
                    <h4 style="	font-size: 1.2em;color: #fff;margin: 0.3em 0em;font-family: 'Carrois Gothic', sans-serif;">
                        累计收益</h4>
                    <p style=" color: #fff;font-size: 0.8em;line-height: 1.8em;">赚了好多！</p>
                </div>
                <div class="col-md-4 market-update-right"
                     style="top:25px;font-size: 3em;color:#337AB7;width: 80px;height: 80px;background: #fff;text-align: center;border-radius: 49px;-o-border-radius:49px;line-height: 1.7em;">
                    <span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="top:10px"></span>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

        <div class="col-md-4">
            <div style="background: #337AB7;margin-right: 0.8em;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);transition: 0.5s all;border-radius: 20px">
                <div class="col-md-8" style="padding-left: 30px;">
                    <h3 style="color: #fff;font-size: 2.5em;font-family: 'Carrois Gothic', sans-serif;">¥<%=hostelProfit%></h3>
                    <h4 style="	font-size: 1.2em;color: #fff;margin: 0.3em 0em;font-family: 'Carrois Gothic', sans-serif;">
                        场馆收益</h4>
                    <p style=" color: #fff;font-size: 0.8em;line-height: 1.8em;">表现不错！</p>
                </div>
                <div class="col-md-4 market-update-right"
                     style="top:25px;font-size: 3em;color:#337AB7;width: 80px;height: 80px;background: #fff;text-align: center;border-radius: 49px;-o-border-radius:49px;line-height: 1.7em;">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true" style="top:10px"></span>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

        <div class="col-md-4">
            <div style="background: #337AB7;margin-right: 0.8em;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);transition: 0.5s all;border-radius: 20px">
                <div class="col-md-8" style="padding-left: 30px;">
                    <h3 style="color: #fff;font-size: 2.5em;font-family: 'Carrois Gothic', sans-serif;"><%=vipCount%>位</h3>
                    <h4 style="	font-size: 1.2em;color: #fff;margin: 0.3em 0em;font-family: 'Carrois Gothic', sans-serif;">
                        会员总计</h4>
                    <p style=" color: #fff;font-size: 0.8em;line-height: 1.8em;">表现不错！</p>
                </div>
                <div class="col-md-4 market-update-right"
                     style="top:25px;font-size: 3em;color:#337AB7;width: 80px;height: 80px;background: #fff;text-align: center;border-radius: 49px;-o-border-radius:49px;line-height: 1.7em;">
                    <span class="glyphicon glyphicon-arrow-up" aria-hidden="true" style="top:10px"></span>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

    </div>
    <fieldset style="margin-top:50px;margin-left: 30px;margin-right: 50px">
        <legend>场馆入住统计</legend>
        <div class="col-md-8 col-md-offset-2" id="checkNums" style="height:300px;">

        </div>
    </fieldset>

    <fieldset style="margin-top: 10px;margin-right: 50px;margin-left: 30px">
        <legend>订单统计</legend>
        <table class="table" style="width: 1000px">
            <thead style="background-color: rgba(190, 188, 198, 0.67)">
            <tr>
                <td>#</td>
                <td>会员编号</td>
                <td>场馆编号</td>
                <td>入座日期</td>
                <td>离座日期</td>
                <td>应付金额</td>
                <td>支付方式</td>
                <td>订单状态</td>
            </tr>
            </thead>
            <tbody>
            <%
                List<Orders> ordersMap = (ArrayList<Orders>) request.getAttribute("ordersList");
                int index = 1;
                for (Orders orders : ordersMap) {
                    String vipNum = orders.getVipNum();
                    String hostelNum = orders.getHostelNum();
                    String checkinDate = orders.getCheckinDate();
                    String checkoutDate = orders.getCheckoutDate();
                    Double requiredMoney = orders.getRequiredMoney();

                    String method = "";
                    PayMethod payMethod = PayMethod.valueOf(orders.getPayMethod());
                    switch (payMethod) {
                        case CASH:
                            method = "现金";
                            break;
                        case CARD:
                            method = "会员卡";
                            break;
                    }

                    String state = "";
                    OrderConditionEnum orderCondition = OrderConditionEnum.valueOf(orders.getOrderCondition());
                    switch (orderCondition) {
                        case BOOK:
                            state = "已预订";
                            break;
                        case VALID:
                            state = "已付款";
                            break;
                        case CHECKIN:
                            state = "已入座";
                            break;
                        case CHECKOUT:
                            state = "已离座";
                            break;
                        case OVERDUE:
                            state = "已过期";
                            break;
                        case CANCEL:
                            state = "已取消";
                            break;
                        case SETTLE:
                            state="已结算";
                            break;
                        default:
                            state = "错误状态";
                            break;
                    }
            %>
            <tr>
                <td><%=index%>
                </td>
                <td><%=vipNum%>
                </td>
                <td><%=hostelNum%>
                </td>
                <td><%=checkinDate%>
                </td>
                <td><%=checkoutDate%>
                </td>
                <td><%=requiredMoney%>
                </td>
                <td><%=method%>
                </td>
                <td><%=state%>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </fieldset>

</div>


<script src="../js/echarts.simple.min.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('checkNums'));
    var checkNums=<%=jsonObject%>;
    var hostel=[];
    for(var item in checkNums){
        hostel.push({
            name:item,
            value:checkNums[item]
        })
    }
    // 指定图表的配置项和数据
    option = {
        backgroundColor: '#ffffff',

        title: {
            text: '消费情况',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#000000'
            }
        },

        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series: [
            {
                name: '场馆名称',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: hostel.sort(function (a, b) {
                    return a.value - b.value
                }),
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(0,0,0, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(0, 0, 0, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },

                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]

    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>

</html>