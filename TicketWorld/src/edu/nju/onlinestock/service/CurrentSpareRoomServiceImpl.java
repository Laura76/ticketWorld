package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.CurrentSpareRoomInfoDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.CurrentSpareRoomInfo;
import edu.nju.onlinestock.service.CurrentSpareRoomService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentSpareRoomServiceImpl implements CurrentSpareRoomService {

    private CurrentSpareRoomInfoDao currentSpareRoomInfoDao=DaoFactory.getCurrentSpareRoomInfoDao();
    private static CurrentSpareRoomServiceImpl currentSpareRoomService=new CurrentSpareRoomServiceImpl();
    /**
     * Default constructor. 
     */	
	private CurrentSpareRoomServiceImpl()
	{
		
	}
	
	public static CurrentSpareRoomService getInstance()
	{
		return currentSpareRoomService;
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
    public List<CurrentSpareRoomInfo> getCurrentSpareRoom(String hostelNum) {
        return currentSpareRoomInfoDao.getInfoByHostel(hostelNum);
    }

    @Override
    public void save(CurrentSpareRoomInfo currentSpareRoomInfo) {
        currentSpareRoomInfoDao.save(currentSpareRoomInfo);
    }

    @Override
    public void update(CurrentSpareRoomInfo currentSpareRoomInfo) {
        currentSpareRoomInfoDao.update(currentSpareRoomInfo);
    }

    @Override
    public CurrentSpareRoomInfo getCurrentSpareRoomByRoomType(String hostelNum, int roomTypeId) {
        return currentSpareRoomInfoDao.getInfoByHostelAndRoomType(hostelNum,roomTypeId);
    }
}
