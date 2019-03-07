<%@ page import="util.RoomTypeEnum" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="util.RoomVO" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Reserve</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../css/jq22.css" />
    <style type="text/css">
            .front{width: 300px;margin: 5px 32px 45px 32px;background-color: #f0f0f0;	color: #666;text-align: center;padding: 3px;border-radius: 5px;}
            .booking_area {float: right;position: relative;width:200px;height: 450px; }
            .booking_area h3 {margin: 5px 5px 0 0;font-size: 16px;}
            .booking_area p{line-height:26px; font-size:16px; color:#999}
            .booking_area p span{color:#666}
            div.seatCharts-cell {color: #182C4E;height: 25px;width: 25px;line-height: 25px;margin: 3px;float: left;text-align: center;outline: none;font-size: 13px;}
            div.seatCharts-seat {color: #fff;cursor: pointer;-webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;}
            div.seatCharts-row {height: 35px;}
            div.seatCharts-seat.available {background-color: #B9DEA0;}
            div.seatCharts-seat.focused {background-color: #76B474;border: none;}
            div.seatCharts-seat.selected {background-color: #E6CAC4;}
            div.seatCharts-seat.unavailable {background-color: #472B34;cursor: not-allowed;}
            div.seatCharts-container {border-right: 1px dotted #adadad;width: 400px;padding: 20px;float: left;}
            div.seatCharts-legend {padding-left: 0px;position: absolute;bottom: 16px;}
            ul.seatCharts-legendList {padding-left: 0px;}
            .seatCharts-legendItem{float:left; width:90px;margin-top: 10px;line-height: 2;}
            span.seatCharts-legendDescription {margin-left: 5px;line-height: 30px;}
            .checkout-button {display: block;width:80px; height:24px; line-height:20px;margin: 10px auto;border:1px solid #999;font-size: 14px; cursor:pointer}
            #seats_chose {max-height: 150px;overflow-y: auto;overflow-x: none;width: 200px;}
            #seats_chose li{float:left; width:72px; height:26px; line-height:26px; border:1px solid #d3d3d3; background:#f7f7f7; margin:6px; font-size:14px; font-weight:bold; text-align:center}
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top" style="color: #d0e9c6">
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
                <li class="active"><a href="searchHostel.action">预订</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="vipInfo.action">会员名: ${id}</a>
                </li>
                <li><a href="hostelRegister_hostelRegister.action">我要开场馆</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
</div>

<img src="../img/pic03.jpg" style="width: 100%;height: 300px;z-index: -1">
<%
    String hostelName = String.valueOf(request.getAttribute("hostelName"));
    String hostelNum = String.valueOf(request.getAttribute("hostelNum"));
    String checkinDate = String.valueOf(request.getAttribute("checkinDate"));
    String checkoutDate = String.valueOf(request.getAttribute("checkoutDate"));
    int KingsizeRoomNum=0;
    int DoubleRoomNum=0;
    int SingleRoomNum=0;
    double KingsizeRoomPrice=0;
    double DoubleRoomPrice=0;
    double SingleRoomPrice=0;
%>
<div>
    <div class="col-md-8">
        <form action="searchRoom" method="post">
            <div style="margin: 50px">
                <label style="padding-left: 3%">入座日期:</label>
                <input type="date" name="checkinDate" style="width: 150px;height: 30px" value="<%=checkinDate%>">
                <label style="padding-left: 3%">离座日期:</label>
                <input type="date" name="checkoutDate" style="width: 150px;height: 30px" value="<%=checkoutDate%>">
                <label style="padding-left: 3%">座位数量:</label>
                <input type="text" name="roomNum" style="width: 50px;height: 30px" value="1">

                <input type="hidden" name="hostelNum" value=<%=hostelNum%>>
                <button class="btn btn-primary" type="submit" style="margin-left: 30px">查询</button>
            </div>
        </form>
		<div>
            	<button type="button" class="btn btn-primary" data-toggle="modal" data-dismiss="modal"
                            data-target="<%="#reserveDetail" %>">选座预约
             </button>
        </div>
        <!--选座预约的提示框-->
            <div class="modal fade" tabindex="-1" role="dialog" id="<%="reserveDetail" %>" style="margin-top: 10%">
                <div class="modal-dialog" role="document">
                    <div class="modal-content" style="width: 700px;height: auto;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" style="align-items: center;padding-left: 3%">场馆预约</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container">
          <h2 class="title">在线选座</h2>
            <div class="demo clearfix">
                <!---左边座位列表----->	
                <div id="seat_area">
                    <div class="front">屏幕</div>					
                </div>
                <!---右边选座信息----->
                <div class="booking_area">
                    <p>座位：</p>
                    <form action="reserveHostelBySeat" method="post">
                    <ul id="seats_chose" name="seat_chose"></ul>
                    <p>票数：<span id="tickects_num" >0</span></p>
                    <p>总价：<b>￥<span id="total_price" >0</span></b></p>
                    <div class="row">
                     <label style="padding:5px">入座日期：<%=checkinDate%></label>
                        <input type="hidden" name="checkinDate" value=<%=checkinDate%>>
                        <label style="padding-left: 20px">离座日期：<%=checkoutDate%></label>
                        <input type="hidden" name="checkoutDate" value=<%=checkoutDate%>>
                        <input type="hidden" name="roomNum" id="roomNum">
 						<input type="hidden" name="requiredMoney" id="requiredMoney">
 						<input type="hidden" name="seatNum" id="seatNum">
 						<label class="form-inline" style="padding: 10px">付款方式：
                                <select name="payMethod">
                                <option value="CARD">会员卡</option>
                                <option value="CASH">现金</option>
                                </select>
                        </label>                       
                        <button type="submit" class="btn">选座预约</button>
                        
                        <div class="col-xs-3" style="float: right">
                        <input type="hidden" name="hostelNum" value=<%=hostelNum%>>
                        <input type="hidden" value="${id}" name="vipNum">
                       </div>
                    </div>
                    </form>
                    <div id="legend"></div>
                </div>
            </div>
        </div>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            
        
        <table style="position:relative;left:30px">
            <%
                String hostelInfo = String.valueOf(request.getAttribute("hostelInfo"));
                List<RoomVO> rooms = (ArrayList<RoomVO>) request.getAttribute("rooms");
                String type="";
                int i=1;
                for (RoomVO roomVO : rooms) {
                    if (roomVO.getRoomType().equals(RoomTypeEnum.KingsizeRoom.toString())) {
                        type = "豪华贵宾座";
                        KingsizeRoomNum=roomVO.getNum();
                        KingsizeRoomPrice=roomVO.getRequiredMoney();
                    } else if (roomVO.getRoomType().equals(RoomTypeEnum.DoubleRoom.toString())) {
                        type = "二等座";
                        DoubleRoomNum=roomVO.getNum();
                        DoubleRoomPrice=roomVO.getRequiredMoney();
                    } else {
                        type = "一等座";
                        SingleRoomNum=roomVO.getNum();
                        SingleRoomPrice=roomVO.getRequiredMoney();
                    }
                    String url="../img/room"+i+".png";
                    String tipNum="reserve"+i;
                    String tipNum2="#reserve"+i;
            %>
            <!--提示框-->
            <div class="modal fade" tabindex="-1" role="dialog" id="<%=tipNum %>" style="margin-top: 10%">
                <div class="modal-dialog" role="document">
                    <div class="modal-content" style="width: 700px;height: auto;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" style="align-items: center;padding-left: 3%">场馆预约</h4>
                        </div>
                        <div class="modal-body">
                            <form action="reserveHostel" method="post">
                                <fieldset>
                                    <legend style="font-size: 16px">预约信息</legend>
                                    <label style="padding: 5px">
                                        场馆名称：
                                        <span name="hostelName"> <%=hostelName%> </span>
                                    </label>
                                    <br>
                                    <%--<label style="padding: 5px">--%>
                                        <%--房间类型：--%>
                                        <input type="hidden" name="roomType" value=<%=roomVO.getRoomType()%>>
                                        <%--<span> <%=roomVO.getRoomType()%> </span>--%>
                                    <%--</label>--%>
                                    <br>
                                    <label style="padding:5px">入座日期：<%=checkinDate%></label>
                                    <input type="hidden" name="checkinDate" value=<%=checkinDate%>>
                                    <label style="padding-left: 20px">离座日期：<%=checkoutDate%></label>
                                    <input type="hidden" name="checkoutDate" value=<%=checkoutDate%>>
                                    <label style="padding-left: 20px">座位数量</label>
                                    <input name="roomNum" >
                                </fieldset>
                                <fieldset style="margin-top: 10px">
                                    <legend style="font-size: 16px;">付款</legend>
                                    <label style="padding: 10px;width: 200px;height: 30px">应付金额:
                                        <span>¥<%=roomVO.getRequiredMoney()%></span>
                                    </label>
                                    <label style="padding: 10px;width: 200px;height: 30px">会员价:
                                        <span>¥<%=(int)roomVO.getRequiredMoney()*0.8%></span>
                                        <input name="requiredMoney" type="hidden" value="<%=roomVO.getRequiredMoney()*0.8%>">
                                    </label>
                                    <label class="form-inline" style="padding: 10px">付款方式：
                                        <select name="payMethod">
                                            <option value="CARD">会员卡</option>
                                            <option value="CASH">现金</option>
                                        </select>
                                    </label>
                                </fieldset>
                                <div class="row">
                                    <div class="col-xs-3" style="float: right">
                                        <input type="hidden" name="hostelNum" value=<%=hostelNum%>>
                                        <input type="hidden" value="${id}" name="vipNum">
                                        <button type="submit" class="btn btn-primary btn-block btn-flat">预约</button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            
            <tr>
                <td style="padding: 10px">
                    <img src="<%=url%>" style="height: 200px;width: 250px">
                </td>
                <td style="padding: 10px;">
                    <h3><%=type%>
                    </h3>
                    <p style="width: 400px;height: 30px">剩余座位数量:<span><%=roomVO.getNum()%></span></p>
                </td>
                <td style="padding:10px;">
                    <h3>¥<%=roomVO.getPrice()%></h3>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-dismiss="modal"
                            data-target="<%=tipNum2 %>">预约
                    </button>
                </td>
            </tr>

            
            <%
                    i++;
                }
            %>
        </table>
    </div>

    <div class="col-md-4">
        <div style="position: relative;margin: 100px">
            <h3><%=hostelName%>
            </h3>
            <p style="height: 100px;width: 100%"><%=hostelInfo%>
            </p>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../js/bootstrap.min.js"></script>
<script src="http://www.jq22.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.seat-charts.min.js"></script>
<script type="text/javascript">
            var price = 0; //电影票价
            var temp1='\'';
            var temp2='\'';
            var temp3='\'';
            var KingsizeRoomNum = <%=KingsizeRoomNum%>;
            var SingleRoomNum=<%=SingleRoomNum%>;
            var DoubleRoomNum=<%=DoubleRoomNum%>;
            var KingsizeRoomPrice=<%=KingsizeRoomPrice%>;
            var SingleRoomPrice=<%=SingleRoomPrice%>;
            var DoubleRoomPrice=<%=DoubleRoomPrice%>;
            var strs=new Array()
            var row1=2;
            if(SingleRoomNum%KingsizeRoomNum==0){ 
            		var row2=2+parseInt(SingleRoomNum/KingsizeRoomNum)+1;
            }else{
            		var row2=2+parseInt(SingleRoomNum/KingsizeRoomNum)+2;
            }
            if(DoubleRoomNum%KingsizeRoomNum==0){ 
        			var row3=row2+parseInt(DoubleRoomNum/KingsizeRoomNum)+1;
        		}else{
        			var row3=row2+parseInt(DoubleRoomNum/KingsizeRoomNum)+2;
        		}
            for (var i=0; i<KingsizeRoomNum; i++)
            {
            		temp1+='c';
            		temp2+='_';
            }
            temp1+='\'';
            temp2+='\'';
			strs.push(temp1);
            strs.push(temp2);
            for (var i=0; i<parseInt(SingleRoomNum/KingsizeRoomNum); i++)
            {
            		strs.push(temp1);
            }
            strs.push(temp2);
            for (var i=0; i<SingleRoomNum%KingsizeRoomNum; i++)
            {
				temp3+='c';
            }
            if(temp3!=='\''){
            		temp3+='\'';
            	 	strs.push(temp3);
            }
            temp3='\'';
            for (var i=0; i<parseInt(DoubleRoomNum/KingsizeRoomNum); i++)
            {
            		strs.push(temp1);
            }
            strs.push(temp2);
            for (var i=0; i<DoubleRoomNum%KingsizeRoomNum; i++)
            {
				temp3+='c';
            }
            if(temp3!=='\''){
            	 	temp3+='\'';
            		strs.push(temp3);
            }
            var seatNums=new Array()
            $(document).ready(function() {
                var $cart = $('#seats_chose'), //座位区
                    $tickects_num = $('#tickects_num'), //票数
                    $total_price = $('#total_price'); //票价总额
                var sc = $('#seat_area').seatCharts({
                    map: strs
                    	/* [//座位结构图 a 代表座位; 下划线 "_" 代表过道
                    		strs
                    ] */,
                    naming: {//设置行列等信息
                        top: false, //不显示顶部横坐标（行） 
                        getLabel: function(character, row, column) { //返回座位信息 
                            return column;
                        }
                    },
                    legend: {//定义图例
                        node: $('#legend'),
                        items: [
                            ['c', 'available', '可选座'],
                            ['c', 'unavailable', '已售出']
                        ]
                    },
                    click: function() {
                        if (this.status() == 'available') { //若为可选座状态，添加座位
                            $('<li>' + (this.settings.row + 1) + '排' + this.settings.label + '座</li>')
                                    .attr('id', 'cart-item-' + this.settings.id)
                                    .data('seatId', this.settings.id)
                                    .appendTo($cart);
                        	    $totalPrice=getTotalPrice(sc,this.settings.row+1);
                            $tickects_num.text(sc.find('selected').length + 1); //统计选票数量
                            $total_price.text($totalPrice);//计算票价总金额
                            document.getElementById('roomNum').value=sc.find('selected').length + 1;
                            document.getElementById('requiredMoney').value=$totalPrice;
                            seatNums.push((this.settings.row+1)+'排'+this.settings.label+'座');
                            document.getElementById('seatNum').value=seatNums;
                            /* var seatNum='[\''+(this.settings.row+1)+'_'+this.settings.label+'\']';
                            sc.get(seatNum).status('unavailable'); */
                           
                            return 'selected';
                        } else if (this.status() == 'selected') { //若为选中状态
                            $tickects_num.text(sc.find('selected').length - 1);//更新票数量
                            $totalPrice2=getTotalPrice2(sc,this.settings.row+1);
                            $total_price.text($totalPrice2);//更新票价总金额
                            $('#cart-item-' + this.settings.id).remove();//删除已预订座位
                            document.getElementById('roomNum').value=sc.find('selected').length - 1;
                            document.getElementById('requiredMoney').value=$totalPrice2;
                             return 'available';
                        } else if (this.status() == 'unavailable') { //若为已售出状态
                            return 'unavailable';
                        } else {
                            return this.style();
                        }
                    }
                });
            });
            var total = 0;
            function getTotalPrice(sc,rowNow) { //计算票价总额
            		if(rowNow<row1){price=KingsizeRoomPrice;} 
            		if(rowNow>row1&&rowNow<row2){price=SingleRoomPrice;} 
            		if(rowNow>row2&&rowNow<row3){price=DoubleRoomPrice;} 
                total += price;
                return total;
            }
            function getTotalPrice2(sc,rowNow) { //计算票价总额
        		if(rowNow<row1){price=KingsizeRoomPrice;} 
        		if(rowNow>row1&&rowNow<row2){price=SingleRoomPrice;} 
        		if(rowNow>row2&&rowNow<row3){price=DoubleRoomPrice;} 
            total -= price;
            return total;
        }
</script>
</body>
</html>
