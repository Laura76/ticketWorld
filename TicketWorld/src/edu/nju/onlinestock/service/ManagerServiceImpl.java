package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.dao.BalanceSettleDao;
import edu.nju.onlinestock.dao.HostelDao;
import edu.nju.onlinestock.dao.ManagerDao;
import edu.nju.onlinestock.dao.OrdersDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.*;
import edu.nju.onlinestock.service.ApprovalService;
import edu.nju.onlinestock.service.ManagerService;
import util.OrderConditionEnum;
import util.SettleConditionEnum;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerServiceImpl implements ManagerService {
    private ApprovalService approvalService;
    
    private static ManagerDao managerDao=DaoFactory.getManagerDao();
    private static BalanceSettleDao balanceSettleDao=DaoFactory.getBalanceSettleDao();
    private static HostelDao hostelDao=DaoFactory.getHostelDao();
    private static OrdersDao ordersDao=DaoFactory.getOrdersDao();
    
	private static ManagerServiceImpl managerService=new ManagerServiceImpl();
	
    /**
     * Default constructor. 
     */	
	private ManagerServiceImpl()
	{
		
	}
	
	public static ManagerService getInstance()
	{
		return managerService;
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
    public boolean checkManager(String managerNum) {
        return managerDao.checkManager(managerNum);
    }

    @Override
    public boolean checkPassword(String managerNum, String password) {
        return managerDao.checkPassword(managerNum, password);
    }

    @Override
    public void save(Manager manager) {
        managerDao.save(manager);
    }

    @Override
    public void update(Manager manager) {
        managerDao.update(manager);
    }

    @Override
    public List<Manager> getAllList() {
        return managerDao.getAllList();
    }

    @Override
    public Manager queryByNum(String managerNum) {
        return managerDao.queryByNum(managerNum);
    }

    @Override
    public String getManagerNum() {
        return managerDao.getManagerNum();
    }

    @Override
    public void approve(List<Approval> approvals) {
        for (Approval approval : approvals) {
            approvalService.update(approval);
        }
    }

    @Override
    public List<BalanceSettle> getWaitSettle(String date) throws ParseException {
        List<Orders> orderses = ordersDao.queryByCondition(OrderConditionEnum.CHECKOUT.toString());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = sdf.parse(date);
//        List<BalanceSettle> balanceSettles=new ArrayList<>();
        for (Orders orders : orderses) {
            String hostelNum = orders.getHostelNum();
            double paidMoney = orders.getPaidMoney();
            //若数据库中已存在该hostelNum并且结算状态为wait，则将paidMoney加到balance上
            //若不存在wait状态的hostelNum，则新建一条结算记录，并将其状态置为wait
            BalanceSettle balanceSettle = balanceSettleDao.getByHostelAndCondition(hostelNum, SettleConditionEnum.WAIT.toString());
            if (balanceSettle != null) {
                double currentBalance = balanceSettle.getBalance();
                balanceSettle.setBalance(currentBalance + paidMoney);
                balanceSettleDao.update(balanceSettle);

                orders.setOrderCondition(OrderConditionEnum.SETTLE.toString());
                ordersDao.update(orders);
//                balanceSettles.add(balanceSettle);
            }else {
                BalanceSettle settle = new BalanceSettle();
                settle.setHostelNum(hostelNum);
                settle.setSettleCondition(SettleConditionEnum.WAIT.toString());
                settle.setBalance(paidMoney);
                settle.setSettleDate(date);
                balanceSettleDao.save(settle);
//                balanceSettles.add(settle);
                orders.setOrderCondition(OrderConditionEnum.SETTLE.toString());
                ordersDao.update(orders);
            }
        }

        List<BalanceSettle> balanceSettles = balanceSettleDao.getAll();
        return balanceSettles;
    }


    @Override
    public List<BalanceSettle> settleBalance(String date, String managerNum) throws ParseException {
        //查找对应经理的账户
        Manager manager = managerDao.queryByNum(managerNum);
        double profit = manager.getProfit();
        //获得所有wait状态的结算记录，将其balance添加到对应的hostel的profit中
        //并将其状态置为settled
        List<BalanceSettle> balanceSettles = balanceSettleDao.getBalanceSettleByCondition(SettleConditionEnum.WAIT.toString());
        for (BalanceSettle balanceSettle : balanceSettles) {

            Hostel hostel = hostelDao.queryHostelByNum(balanceSettle.getHostelNum());
            int currentBalance =(int) (balanceSettle.getBalance() * 0.8);
            double currentProfit = hostel.getProfit();
            hostel.setProfit(currentProfit + currentBalance);
            hostelDao.updateHostel(hostel);

            balanceSettle.setSettleCondition(SettleConditionEnum.SETTLED.toString());
            balanceSettleDao.update(balanceSettle);

            profit = profit + balanceSettle.getBalance() -currentBalance;
        }
        manager.setProfit(profit);
        managerDao.update(manager);

        List<BalanceSettle> settles = balanceSettleDao.getAll();
        return settles;
    }
}
