package edu.nju.onlinestock.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nju.onlinestock.dao.RoomTypeDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.RoomType;
import edu.nju.onlinestock.service.RoomTypeService;

public class RoomTypeServiceImpl implements RoomTypeService {
    RoomTypeDao roomTypeDao=DaoFactory.getRoomTypeDao();
    private static RoomTypeServiceImpl roomTypeService=new RoomTypeServiceImpl();
    /**
     * Default constructor. 
     */	
	private RoomTypeServiceImpl()
	{
		
	}
	
	public static RoomTypeService getInstance()
	{
		return roomTypeService;
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
    public RoomType find(int id) {
        return roomTypeDao.find(id);
    }

    @Override
    public RoomType queryByType(String type) {
        return roomTypeDao.queryByType(type);
    }
}
