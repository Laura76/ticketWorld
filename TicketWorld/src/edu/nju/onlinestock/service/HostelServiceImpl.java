package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.dao.HostelDao;
import edu.nju.onlinestock.dao.RoomTypeDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.Hostel;
import edu.nju.onlinestock.service.HostelService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HostelServiceImpl implements HostelService {
    
    private static RoomTypeDao roomTypeDao=DaoFactory.getRoomTypeDao();
    
    private static HostelDao hostelDao=DaoFactory.getHostelDao();
	
	private static HostelServiceImpl hostelService=new HostelServiceImpl();
    /**
     * Default constructor. 
     */	
	private HostelServiceImpl()
	{
		
	}
	
	public static HostelService getInstance()
	{
		return hostelService;
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
    public boolean checkHostel(String hostelNum) {
        return hostelDao.checkHostel(hostelNum);
    }

    @Override
    public boolean checkApprove(String hostelNum) {
        return hostelDao.checkApprove(hostelNum);
    }

    @Override
    public boolean checkPassword(String hostelNum, String hostelPassword) {
        return hostelDao.checkPassword(hostelNum,hostelPassword);
    }

    @Override
    public void registerHostel(Hostel hostel) {
        hostelDao.save(hostel);
    }

    @Override
    public String getHostelNum() {
        return hostelDao.getHostelNum();
    }

    @Override
    public void updateHostel(Hostel hostel) {
        hostelDao.updateHostel(hostel);
    }

    @Override
    public void deleteHostel(String hostelNum) {
        hostelDao.deleteHostel(hostelNum);
    }

    @Override
    public List<Hostel> queryHostelByProvince(String province) {
        return hostelDao.queryHostelByProvince(province);
    }

    @Override
    public List<Hostel> queryHostelByCity(String city) {
        return hostelDao.queryHostelByCity(city);
    }

    @Override
    public List<Hostel> queryHostelByName(String hostelName) {
        return hostelDao.queryHostelByName(hostelName);
    }

    @Override
    public Hostel queryHostelByNum(String hostelNum) {
        return hostelDao.queryHostelByNum(hostelNum);
    }

    @Override
    public List<Hostel> queryHostelByLevel(int level) {
        return hostelDao.queryHostelByLevel(level);
    }

    @Override
    public List<Hostel> queryAll() {
        return hostelDao.queryAll();
    }

    @Override
    public List<Hostel> queryByApprove(String approveState){
        return hostelDao.queryByApprove(approveState);
    }

}
