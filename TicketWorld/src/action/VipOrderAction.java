package action;

import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.service.HostelService;
import edu.nju.onlinestock.service.OrdersService;
import util.OrderConditionEnum;
import util.OrderVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VipOrderAction extends BaseAction{
    OrdersService ordersService=ServiceFactory.getOrdersService();
    HostelService hostelService=ServiceFactory.getHostelService();
    
    public String getVipOrders(){
        String vipNum=String.valueOf(request.getSession().getAttribute("id"));
        List<Orders> ordersList=ordersService.queryByVip(vipNum);
        //未支付的订单检查是否超过15分钟，超过则取消订单（用十秒钟测试）
        for(Orders orders:ordersList) {
        		if(orders.getOrderCondition().equals(OrderConditionEnum.BOOK.toString())) {
        			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
        	        String currentTime=df.format(System.currentTimeMillis());
					try {
						Date one = df.parse(orders.getOrderTime());
						Date two = df.parse(currentTime);
						long time1 = one.getTime();
	        				long time2 = two.getTime();
	        				long diff = time2 - time1;
	        				long day = diff / (24 * 60 * 60 * 1000);
	        				long hour = (diff / (60 * 60 * 1000) - day * 24);
	        				long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
	        				if(min>1) {
	        					orders.setOrderCondition(OrderConditionEnum.CANCEL.toString());
	        				}
					} catch (ParseException e) {
						e.printStackTrace();
					}
        		}
        }
        List<OrderVO> orderVOs=this.getOrderVOs(ordersList);
        request.setAttribute("orders",orderVOs);
        return "orders";
    }

    public String cancelOrder(){
        String orderNum=request.getParameter("orderNum");
        if(ordersService.cancel(orderNum)){
            String vipNum=String.valueOf(request.getSession().getAttribute("id"));
            List<Orders> ordersList=ordersService.queryByVip(vipNum);
            List<OrderVO> orderVOs=this.getOrderVOs(ordersList);
            request.setAttribute("orders",orderVOs);
            return "cancel";
        }
        return "fail";
    }

    private List<OrderVO> getOrderVOs(List<Orders> ordersList){
        List<OrderVO> orderVOs=new ArrayList<>();
        for(Orders orders:ordersList){
            OrderVO orderVO=new OrderVO();
            String hostelName=hostelService.queryHostelByNum(orders.getHostelNum()).getHostelName();
            orderVO.setOrderNum(orders.getOrderNum());
            orderVO.setHostelName(hostelName);
            orderVO.setCheckinDate(orders.getCheckinDate());
            orderVO.setCheckoutDate(orders.getCheckoutDate());
            orderVO.setPayMethod(orders.getPayMethod());
            orderVO.setOrderCondition(orders.getOrderCondition());
            orderVO.setRequiredMoney(orders.getRequiredMoney());
            System.out.println("requiredMoney"+orders.getRequiredMoney());
            orderVOs.add(orderVO);
        }
        return orderVOs;
    }
}
