package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.*;
import edu.nju.onlinestock.service.*;
import util.CheckConditionEnum;
import util.OrderConditionEnum;
import util.RoomPlanVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
public class HostelAction extends BaseAction {
	int times=0;
    HostelService hostelService=ServiceFactory.getHostelService();
    RoomTypeService roomTypeService=ServiceFactory.getRoomTypeService();
    RoomPlanService roomPlanService=ServiceFactory.getRoomPlanService();
    OrdersService ordersService=ServiceFactory.getOrdersService();
    CheckInfoService checkInfoService=ServiceFactory.getCheckInfoService();
    VipService vipService=ServiceFactory.getVipService();
    CurrentSpareRoomService currentSpareRoomService=ServiceFactory.getCurrentSpareRoomService();

    public static void main(String[] args) {
    		String startDate="2018-03-14";
    		//房间计划存到数据库之前先定时（开始时间前两周需要配票）--当场馆已经将三种座位计划都发布之后
    		Timer timer = new Timer();
    		// 时间类
        Calendar start = Calendar.getInstance();
        start.set(Integer.parseInt(startDate.substring(0, 4)), Integer.parseInt(startDate.substring(5, 7))-1, Integer.parseInt(startDate.substring(8)));
        start.add(Calendar.WEEK_OF_YEAR, -2);
        System.out.println(start.getTime());
    }
    public String getPlan() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        List<RoomPlan> roomPlans = roomPlanService.queryByHostel(hostelNum);
        if(roomPlans.size()==3) {
        		String startDate=roomPlans.get(0).getStartDate();
        		String endDate=roomPlans.get(0).getEndDate();
        		System.out.print(hostelNum +"  "+startDate);
        		VoteAllocation voteAllocation=new VoteAllocation(hostelNum,startDate,endDate);
        		voteAllocation.run();
        }
        request.setAttribute("roomPlan", this.getPlanVO(roomPlans));
        
        return "plan";
    }

    public String hostelPlan() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        String roomType = request.getParameter("roomType");
        double roomPrice = Double.valueOf(request.getParameter("roomPrice"));
        int roomNum = Integer.valueOf(request.getParameter("roomNum"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String showType=request.getParameter("showType");
        String showInfo=request.getParameter("showInfo");
        
        RoomPlan roomPlan = new RoomPlan();
        roomPlan.setRoomTypeId(roomTypeService.queryByType(roomType).getId());
        roomPlan.setHostelNum(hostelNum);
        roomPlan.setRoomNum(roomNum);
        roomPlan.setRoomPrice(roomPrice);
        roomPlan.setStartDate(startDate);
        roomPlan.setEndDate(endDate);
        roomPlan.setShowInfo(showInfo);
        roomPlan.setShowType(showType);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        roomPlan.setDate(sdf.format(date));
        times++;
        if(times%3==0) {
        		//房间计划存到数据库之前先定时（开始时间前两周需要配票）--当场馆已经将三种座位计划都发布之后
        		Timer timer = new Timer();
        		// 时间类
            Calendar start = Calendar.getInstance();
            start.set(Integer.parseInt(startDate.substring(0, 4)), Integer.parseInt(startDate.substring(5, 7))-1, Integer.parseInt(startDate.substring(8)));
            start.add(Calendar.WEEK_OF_YEAR, -2);
            //使用房间计划完成后五分钟之后配票来测试
            //timer.schedule(new VoteAllocation(hostelNum,startDate,endDate),1000*60*5 );
            //设置开始执行的时间为 某年-某月-某月 00:00:00
        		//timer.schedule(new VoteAllocation(hostelNum,startDate,endDate),start.getTime() );
        }
        roomPlanService.saveRoomPlan(roomPlan);

        CurrentSpareRoomInfo currentSpareRoomInfo=new CurrentSpareRoomInfo();
        currentSpareRoomInfo.setHostelNum(hostelNum);
        currentSpareRoomInfo.setRoomTypeId(roomTypeService.queryByType(roomType).getId());
        currentSpareRoomInfo.setSpareNum(roomNum);
        currentSpareRoomService.save(currentSpareRoomInfo);

        List<RoomPlan> roomPlans = roomPlanService.queryByHostel(hostelNum);
        request.setAttribute("roomPlan", this.getPlanVO(roomPlans));
        return "hostelPlan";
    }

    public String getCheckInfo() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        List<CheckInfo> checkInfos = checkInfoService.getCheckInfoByHostel(hostelNum);
        List<CheckInfo> checkInfoList = new ArrayList<>();
        for (CheckInfo checkInfo : checkInfos) {
            if (checkInfo.getCheckCondition().equals(CheckConditionEnum.CHECKIN.toString())) {
                checkInfoList.add(checkInfo);
            }
        }

        request.setAttribute("check", checkInfoList);
        request.setAttribute("type", "all");
        return "check";
    }

    public String vipCheckin() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        String lodgerName = request.getParameter("lodgerName");
        String checkinDate = request.getParameter("checkinDate");
        String checkoutDate = request.getParameter("checkoutDate");
        double paidMoney = Double.valueOf(request.getParameter("paidMoney"));
        String roomNum = request.getParameter("roomNumber");
        int roomTypeId = Integer.valueOf(request.getParameter("roomTypeId"));
        String orderNum = request.getParameter("orderNum");

        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setHostelNum(hostelNum);
        checkInfo.setLodgerName(lodgerName);
        checkInfo.setPaidMoney(paidMoney);
        checkInfo.setCheckinDate(checkinDate);
        checkInfo.setCheckoutDate(checkoutDate);
        checkInfo.setCheckCondition(CheckConditionEnum.CHECKIN.toString());
        checkInfo.setRoomNum(roomNum);
        checkInfo.setRoomTypeId(roomTypeId);
        checkInfo.setOrderNum(orderNum);
        checkInfoService.save(checkInfo);

        Orders orders = ordersService.find(orderNum);
        orders.setOrderCondition(OrderConditionEnum.CHECKIN.toString());

        List<CheckInfo> checkInfos = checkInfoService.getCheckInfoByHostel(hostelNum);
        List<CheckInfo> checkInfoList = new ArrayList<>();
        for (CheckInfo info : checkInfos) {
            if (info.getCheckCondition().equals(CheckConditionEnum.CHECKIN.toString())) {
                checkInfoList.add(info);
            }
        }

        request.setAttribute("check", checkInfos);
        request.setAttribute("type", "all");
        return "check";
    }

    public String getVipOrder() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        String vipNum = request.getParameter("vipNum");
        List<Orders> ordersList = ordersService.queryByVip(vipNum);
        List<Orders> orderses = new ArrayList<>();
        for (Orders orders : ordersList) {
            if (orders.getHostelNum().equals(hostelNum) && (orders.getOrderCondition().equals(OrderConditionEnum.VALID.toString()) || orders.getOrderCondition().equals(OrderConditionEnum.BOOK.toString()))) {
                orderses.add(orders);
            }
        }
        request.setAttribute("vipOrder", orderses);
        request.setAttribute("type", "vip");
        return "vipOrder";
    }

    public String checkInfo() {
    		String vipNum=request.getParameter("vipNum");
    		System.out.println(vipNum);
    		if(vipNum.length()!=0&&!vipService.isExist(vipNum)) {
    			return "fail";
    		}
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        String roomType = request.getParameter("roomType");
        String roomNum = request.getParameter("roomNum");
        String lodgerName = request.getParameter("lodgerName");
        String checkinDate = request.getParameter("checkinDate");
        String chekoutDate = request.getParameter("checkoutDate");
        double paidMoney = Double.valueOf(request.getParameter("paidMoney"));
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setHostelNum(hostelNum);
        checkInfo.setRoomTypeId(roomTypeService.queryByType(roomType).getId());
        checkInfo.setLodgerName(lodgerName);
        checkInfo.setPaidMoney(paidMoney);
        checkInfo.setCheckCondition(CheckConditionEnum.CHECKIN.toString());
        checkInfo.setCheckinDate(checkinDate);
        checkInfo.setCheckoutDate(chekoutDate);
        checkInfo.setRoomNum(roomNum);
        checkInfoService.save(checkInfo);

        List<CheckInfo> checkInfos = checkInfoService.getCheckInfoByHostel(hostelNum);
        List<CheckInfo> checkInfoList = new ArrayList<>();
        for (CheckInfo info : checkInfos) {
            if (info.getCheckCondition().equals(CheckConditionEnum.CHECKIN.toString())) {
                checkInfoList.add(info);
            }
        }
        request.setAttribute("check", checkInfoList);
        request.setAttribute("type", "all");
        return "checkInfo";
    }

    public String checkout() {
        String hostelNum = String.valueOf(request.getSession().getAttribute("id"));
        int checkNum = Integer.valueOf(request.getParameter("checkNum"));

        CheckInfo checkInfo = checkInfoService.find(checkNum);
        checkInfo.setCheckCondition(CheckConditionEnum.CHECKOUT.toString());
        checkInfoService.update(checkInfo);

        //判断是否为会员
        if (checkInfo.getOrderNum() != null) {
            Orders orders = ordersService.find(checkInfo.getOrderNum());
            orders.setOrderCondition(OrderConditionEnum.CHECKOUT.toString());
            ordersService.updateOrders(orders);
        }

        List<CheckInfo> checkInfos = checkInfoService.getCheckInfoByHostel(hostelNum);
        List<CheckInfo> checkInfoList = new ArrayList<>();
        for (CheckInfo info : checkInfos) {
            if (info.getCheckCondition().equals(CheckConditionEnum.CHECKIN.toString())) {
                checkInfoList.add(info);
            }
        }
        request.setAttribute("check", checkInfoList);
        request.setAttribute("type", "all");
        return "out";
    }

    private List<RoomPlanVO> getPlanVO(List<RoomPlan> roomPlans) {
        List<RoomPlanVO> roomPlanVOs = new ArrayList<>();
        for (RoomPlan roomPlan : roomPlans) {
            RoomPlanVO roomPlanVO = new RoomPlanVO();
            roomPlanVO.setHostelNum(roomPlan.getHostelNum());
            roomPlanVO.setDate(roomPlan.getDate());
            roomPlanVO.setStartDate(roomPlan.getStartDate());
            roomPlanVO.setEndDate(roomPlan.getEndDate());
            roomPlanVO.setRoomNum(roomPlan.getRoomNum());
            roomPlanVO.setRoomType(roomTypeService.find(roomPlan.getRoomTypeId()).getRoomType());
            roomPlanVOs.add(roomPlanVO);
        }
        return roomPlanVOs;
    }

    public String hostelSta() {
        //累计收益 totalProfit 订单消费 orderProfit
        //订单列表 ordersList
        String hostelNum=String.valueOf(request.getSession().getAttribute("id"));
        Hostel hostel=hostelService.queryHostelByNum(hostelNum);
        double totalProfit=hostel.getProfit();
        double orderProfit=0;
        List<Orders> ordersList=ordersService.queryByHostel(hostelNum);
        for(Orders orders:ordersList){
            orderProfit=orderProfit+orders.getPaidMoney();
        }
        request.setAttribute("totalProfit",totalProfit);
        request.setAttribute("orderProfit",orderProfit);
        request.setAttribute("ordersList",ordersList);
        return "hostelSta";
    }
}
