package edu.nju.onlinestock.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nju.onlinestock.dao.DiscountStrategyDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.DiscountStrategy;
import edu.nju.onlinestock.service.DiscountStrategyService;

public class DiscountStrategyServiceImpl implements DiscountStrategyService {

    private DiscountStrategyDao discountStrategyDao=DaoFactory.getDiscountStrategyDao();
    private static DiscountStrategyServiceImpl discountStrategyService=new DiscountStrategyServiceImpl();
    /**
     * Default constructor. 
     */	
	private DiscountStrategyServiceImpl()
	{
		
	}
	
	public static DiscountStrategyService getInstance()
	{
		return discountStrategyService;
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
    public DiscountStrategy getDiscount(int level) {
        return discountStrategyDao.find(level);
    }
}
