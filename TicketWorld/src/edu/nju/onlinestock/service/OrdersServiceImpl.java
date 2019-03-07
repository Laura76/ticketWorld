package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.OrdersDao;
import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.factory.ServiceFactory;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.CurrentSpareRoomService;
import edu.nju.onlinestock.service.OrdersService;
import edu.nju.onlinestock.service.VipBalanceService;
import util.OrderConditionEnum;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrdersServiceImpl implements OrdersService {
    private OrdersDao ordersDao=DaoFactory.getOrdersDao();
    private VipDao vipDao=DaoFactory.getVipDao();
    VipBalanceService vipBalanceService=ServiceFactory.getVipBalanceService();
    CurrentSpareRoomService currentSpareRoomService=ServiceFactory.getCurrentSpareRoomService();
    
    private static OrdersServiceImpl ordersService=new OrdersServiceImpl();
    /**
     * Default constructor. 
     */	
	private OrdersServiceImpl()
	{
		
	}
	
	public static OrdersService getInstance()
	{
		return ordersService;
	}
	
	public void sentErrorMessage(String message,HttpServletRequest req) 
			throws ServletException,IOException
{
		req.setAttribute("message",message);
//		RequestDispatcher dispater=req.getRequestDispatcher(resp.encodeURL("/error/error.jsp"));
//		dispater.forward(req,resp);
}


	public void sentMessage(String message,HttpServletRequest req) 
			throws ServletException,IOException
{
		req.setAttribute("message",message);
//		RequestDispatcher dispater=req.getRequestDispatcher(resp.encodeURL("/message/message.jsp"));
//		dispater.forward(req,resp);
}


	public void forwardPage(String page,HttpServletRequest req,HttpServletResponse resp) 
			throws ServletException,IOException
{
		RequestDispatcher dispater=req.getRequestDispatcher(resp.encodeURL(page));
		dispater.forward(req,resp);
}
    @Override
    public void saveOrders(Orders orders) {
        ordersDao.save(orders);
    }

    @Override
    public String getOrderNum(String hostelNum) {
        return ordersDao.getOrderNum(hostelNum);
    }

    @Override
    public void updateOrders(Orders orders) {
        ordersDao.update(orders);
    }

    @Override
    public boolean pay(String orderNum, double money) {
        Orders orders=ordersDao.find(orderNum);
        if(null!=orders){
            double requiredMoney=orders.getRequiredMoney();
            if(money==requiredMoney){
                if(vipBalanceService.withdraw(orders.getVipNum(),money)>=0){
                    orders.setPaidMoney(money);
                    orders.setOrderCondition(OrderConditionEnum.VALID.toString());
                    ordersDao.update(orders);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cancel(String orderNum) {
        Orders orders=ordersDao.find(orderNum);
        if(null!=orders&&(orders.getOrderCondition().equals(OrderConditionEnum.VALID.toString())||(orders.getOrderCondition().equals(OrderConditionEnum.BOOK.toString())))){
            double requiredMoney=orders.getRequiredMoney();
            double paidMoney=orders.getPaidMoney();
            if(paidMoney==requiredMoney){
                //收取30%的手续费，其他费用退还
                orders.setPaidMoney(paidMoney-requiredMoney*0.7);
                orders.setOrderCondition(OrderConditionEnum.CANCEL.toString());
                ordersDao.update(orders);
                Vip vip=vipDao.find(orders.getVipNum());
                if(null!=vip){
                    double currentMoney=vip.getMoney();
                    vip.setMoney(currentMoney+requiredMoney*0.7);
                    vipDao.update(vip);
                    return true;
                }
            }else{
                orders.setOrderCondition(OrderConditionEnum.CANCEL.toString());
                ordersDao.update(orders);
            }
            return true;
        }
        return false;
    }

    @Override
    public Orders find(String orderNum) {
        return ordersDao.find(orderNum);
    }

    @Override
    public List<Orders> queryByHostel(String hostelNum) {
        return ordersDao.queryByHostel(hostelNum);
    }

    @Override
    public List<Orders> queryByVip(String vipNum) {
        return ordersDao.queryByVip(vipNum);
    }

    @Override
    public List<Orders> queryByHostelAndCheckin(String hostelNum, String checkinDate) {
        return ordersDao.queryByHostelAndCheckin(hostelNum,checkinDate);
    }

    @Override
    public List<Orders> queryByHostelAndCheckout(String hostelNum, String checkoutDate) {
        return ordersDao.queryByHostelAndCheckout(hostelNum, checkoutDate);
    }

    @Override
    public List<Orders> queryByState(String state) {
        return ordersDao.queryByCondition(state);
    }
}
