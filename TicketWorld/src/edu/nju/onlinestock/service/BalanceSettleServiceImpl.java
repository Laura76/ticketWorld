package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.BalanceSettleDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.BalanceSettle;
import edu.nju.onlinestock.service.BalanceSettleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BalanceSettleServiceImpl implements BalanceSettleService {
    BalanceSettleDao balanceSettleDao=DaoFactory.getBalanceSettleDao();

    private static BalanceSettleServiceImpl balanceSettleService=new BalanceSettleServiceImpl();
    /**
     * Default constructor. 
     */	
	private BalanceSettleServiceImpl()
	{
		
	}
	
	public static BalanceSettleService getInstance()
	{
		return balanceSettleService;
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
    public List<BalanceSettle> getAll(String date) {
        return balanceSettleDao.getBalanceSettleByDate(date);
    }

    @Override
    public List<BalanceSettle> getAllList() {
        return balanceSettleDao.getAll();
    }


}
