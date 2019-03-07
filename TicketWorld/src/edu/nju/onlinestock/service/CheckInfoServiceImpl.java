package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.CheckInfoDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.CheckInfo;
import edu.nju.onlinestock.service.CheckInfoService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CheckInfoServiceImpl implements CheckInfoService {
    private CheckInfoDao checkInfoDao=DaoFactory.getCheckInfoDao();
    private static CheckInfoServiceImpl checkInfoService=new CheckInfoServiceImpl();
    /**
     * Default constructor. 
     */	
	private CheckInfoServiceImpl()
	{
		
	}
	
	public static CheckInfoService getInstance()
	{
		return checkInfoService;
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
    public void save(CheckInfo checkInfo) {
        checkInfoDao.save(checkInfo);
    }

    @Override
    public CheckInfo find(int checkNum) {
        return checkInfoDao.find(checkNum);
    }

    @Override
    public void update(CheckInfo checkInfo) {
        checkInfoDao.update(checkInfo);
    }

    @Override
    public boolean pay(int checkNum, double money) {
        CheckInfo checkInfo=checkInfoDao.find(checkNum);
        if(null!=checkInfo) {
            double currentPaid = checkInfo.getPaidMoney();
            checkInfo.setPaidMoney(currentPaid + money);
            checkInfoDao.update(checkInfo);
            return true;
        }
        return false;
    }

    @Override
    public List<CheckInfo> getCheckInfoByHostel(String hostelNum) {
        return checkInfoDao.getCheckInfoByHostel(hostelNum);
    }

    @Override
    public List<CheckInfo> getCheckInfoByHostelAndLodger(String hostelNum, String lodgerNum) {
        return checkInfoDao.getCheckInfoByHostelAndLodger(hostelNum,lodgerNum);
    }

    @Override
    public List<CheckInfo> getCheckInfoByCheckin(String hostelNum, String checkinDate) {
        return checkInfoDao.getCheckInfoByCheckin(hostelNum,checkinDate);
    }

    @Override
    public List<CheckInfo> getCheckInfoBycCheckout(String hostelNum, String checkoutDate) {
        return checkInfoDao.getCheckInfoBycCheckout(hostelNum,checkoutDate);
    }

    @Override
    public List<CheckInfo> getCheckInfoBetween(String startDate, String endDate) {
        return checkInfoDao.getCheckInfoBetween(startDate,endDate);
    }
}
