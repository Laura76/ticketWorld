package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.CheckInfo;
import edu.nju.onlinestock.model.CurrentSpareRoomInfo;
import edu.nju.onlinestock.model.Hostel;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.model.RoomPlan;
import edu.nju.onlinestock.service.*;
import util.ApprovalStateEnum;
import util.OrderConditionEnum;
import util.PayMethod;
import util.RoomTypeEnum;
import util.RoomVO;

import java.text.SimpleDateFormat;
import java.util.*;
public class VipReserveAction extends BaseAction {

    HostelService hostelService=ServiceFactory.getHostelService();
    CurrentSpareRoomService currentSpareRoomService=ServiceFactory.getCurrentSpareRoomService();
    RoomTypeService roomTypeService=ServiceFactory.getRoomTypeService();
    OrdersService ordersService=ServiceFactory.getOrdersService();
    CheckInfoService checkInfoService=ServiceFactory.getCheckInfoService();
    RoomPlanService roomPlanService=ServiceFactory.getRoomPlanService();

    public String hostelSearch() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String d1=sdf.format(date);
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,1);
        String d2=sdf.format(c.getTime());

        String checkinDate = request.getParameter("checkinDate");
        if(checkinDate==null){
            checkinDate=d1;
        }
        String checkoutDate = request.getParameter("checkoutDate");
        if(checkoutDate==null){
            checkoutDate=d2;
        }

        String city = request.getParameter("city");
        List<Hostel> hostelList;
        if (city != null) {
            hostelList = hostelService.queryHostelByCity(city);
        } else {
            hostelList = hostelService.queryByApprove(ApprovalStateEnum.APPROVE.toString());
        }
        if (hostelList != null) {
            request.setAttribute("hostels", hostelList);
            request.setAttribute("city", city);
            request.setAttribute("checkinDate", checkinDate);
            request.setAttribute("checkoutDate", checkoutDate);
            System.out.println(hostelList.size());
            return "hostels";
        } else {
            return "notFound";
        }

    }

    public String roomSearch() throws Exception {
        String hostelNum = request.getParameter("hostelNum");
        String checkinDate = request.getParameter("checkinDate");
        String checkoutDate = request.getParameter("checkoutDate");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int days=(int)((sdf.parse(checkoutDate).getTime()-sdf.parse(checkinDate).getTime())/86400000);
        System.out.println("day "+days);
        List<CheckInfo> checkInfos = checkInfoService.getCheckInfoBetween(checkinDate, checkoutDate);
        HashMap<String, Integer> rooms = new HashMap<>();
        for (CheckInfo checkInfo : checkInfos) {
            String roomType = roomTypeService.find(checkInfo.getRoomTypeId()).getRoomType();
            if (rooms.keySet().contains(roomType)) {
                int roomNum = rooms.get(roomType) + 1;
                rooms.replace(roomType, roomNum);
            } else {
                rooms.put(roomType, 1);
            }
        }
        //获得座位计划
        List<RoomVO> roomVOs = new ArrayList<>();
        List<RoomPlan> roomPlans = roomPlanService.queryNewestRoomPlan(hostelNum);
        if(roomPlans!=null) {
            for (RoomPlan roomPlan : roomPlans) {
                RoomVO roomVO = new RoomVO();
                String roomType = roomTypeService.find(roomPlan.getRoomTypeId()).getRoomType();
                roomVO.setRoomType(roomType);
                if (rooms.keySet().contains(roomType)) {
                    roomVO.setNum(roomPlan.getRoomNum() - rooms.get(roomType));
                    roomVO.setPrice(roomPlan.getRoomPrice());
                } else {
                    roomVO.setNum(roomPlan.getRoomNum());
                    roomVO.setPrice(roomPlan.getRoomPrice());
                }
                roomVO.setRequiredMoney(roomVO.getPrice()*days);
                roomVOs.add(roomVO);
            }

            request.setAttribute("rooms", roomVOs);
            Hostel hostel = hostelService.queryHostelByNum(hostelNum);
            request.setAttribute("hostelName", hostel.getHostelName());
            request.setAttribute("hostelInfo", hostel.getHostelInfo());
            request.setAttribute("checkinDate", checkinDate);
            request.setAttribute("checkoutDate", checkoutDate);
            request.setAttribute("hostelNum", hostelNum);
            return "rooms";
        }else {
            return "notFound";
        }
}
    public String hostelReserve() throws Exception {
        String hostelNum = request.getParameter("hostelNum");
        String vipNum = request.getParameter("vipNum");
        int roomNum = Integer.parseInt(request.getParameter("roomNum"));
        if(roomNum>20) {
        		return "muchRoomNum";
        }
        String roomType = request.getParameter("roomType");
        String requiredMoney = String.valueOf(Double.parseDouble(request.getParameter("requiredMoney"))*roomNum);
        String checkinDate = request.getParameter("checkinDate");
        String checkoutDate = request.getParameter("checkoutDate");
        String payMethod = request.getParameter("payMethod");
        Orders orders = new Orders();
        int roomTypeId=roomTypeService.queryByType(roomType).getId();
        String orderNum = ordersService.getOrderNum(hostelNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
        String orderTime=df.format(System.currentTimeMillis());
        orders.setOrderTime(orderTime);
        orders.setOrderNum(orderNum);
        orders.setRoomTypeId(roomTypeId);
        orders.setHostelNum(hostelNum);
        orders.setVipNum(vipNum);
        orders.setRoomNum(roomNum);
        orders.setRequiredMoney(Double.valueOf(requiredMoney));
        orders.setOrderCondition(OrderConditionEnum.BOOK.toString());
        orders.setCheckinDate(checkinDate);
        orders.setCheckoutDate(checkoutDate);
        orders.setPayMethod(payMethod);
        ordersService.saveOrders(orders);
        
        if (payMethod.equals(PayMethod.CARD.toString())) {
            if (ordersService.pay(orderNum, Double.valueOf(requiredMoney))) {
            		/*
            		修改当前空房间的数量
            		CurrentSpareRoomInfo currentSpareRoomInfo=currentSpareRoomService.getCurrentSpareRoomByRoomType(hostelNum, roomTypeId);
            		currentSpareRoomInfo.setSpareNum(currentSpareRoomInfo.getSpareNum()-roomNum);
            		currentSpareRoomService.update(currentSpareRoomInfo);
            		*/
                return "orders";
            } else {
                return "notEnoughMoney";
            }
        }
        return "orders";
    }
    public String hostelReserveBySeat() throws Exception {
        String hostelNum = request.getParameter("hostelNum");
        String vipNum = request.getParameter("vipNum");
        int roomNum = Integer.parseInt(request.getParameter("roomNum"));
        String seatNum=request.getParameter("seatNum");
        if(roomNum>6) {
        		return "muchRoomNumBySeat";
        }
        String requiredMoney = String.valueOf(Double.parseDouble(request.getParameter("requiredMoney")));
        String checkinDate = request.getParameter("checkinDate");
        String checkoutDate = request.getParameter("checkoutDate");
        String payMethod = request.getParameter("payMethod");
        Orders orders = new Orders();
        String orderNum = ordersService.getOrderNum(hostelNum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
        String orderTime=df.format(System.currentTimeMillis());
        orders.setSeatNum(seatNum);
        orders.setOrderTime(orderTime);
        orders.setOrderNum(orderNum);
        orders.setHostelNum(hostelNum);
        orders.setVipNum(vipNum);
        orders.setRoomNum(roomNum);
        orders.setRequiredMoney(Double.valueOf(requiredMoney));
        orders.setOrderCondition(OrderConditionEnum.BOOK.toString());
        orders.setCheckinDate(checkinDate);
        orders.setCheckoutDate(checkoutDate);
        orders.setPayMethod(payMethod);
        ordersService.saveOrders(orders);
        
        if (payMethod.equals(PayMethod.CARD.toString())) {
            if (ordersService.pay(orderNum, Double.valueOf(requiredMoney))) {
            		/*
            		修改当前空房间的数量
            		CurrentSpareRoomInfo currentSpareRoomInfo=currentSpareRoomService.getCurrentSpareRoomByRoomType(hostelNum, roomTypeId);
            		currentSpareRoomInfo.setSpareNum(currentSpareRoomInfo.getSpareNum()-roomNum);
            		currentSpareRoomService.update(currentSpareRoomInfo);
            		*/
                return "orders";
            } else {
                return "notEnoughMoney";
            }
        }
        return "orders";
    }
}
