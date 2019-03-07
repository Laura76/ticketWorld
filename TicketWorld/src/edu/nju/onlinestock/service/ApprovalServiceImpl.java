package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.Approval;
import edu.nju.onlinestock.service.ApprovalService;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApprovalServiceImpl implements ApprovalService{

    private static ApprovalDao approvalDao=DaoFactory.getApprovalDao();
	
	private static ApprovalServiceImpl approvalService=new ApprovalServiceImpl();
    /**
     * Default constructor. 
     */	
	private ApprovalServiceImpl()
	{
		
	}
	
	public static ApprovalService getInstance()
	{
		return approvalService;
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
    public Approval find(int approvalNum) {
        return approvalDao.find(approvalNum);
    }

    @Override
    public void save(Approval approval) {
        approvalDao.save(approval);
    }

    @Override
    public void update(Approval approval) {
        approvalDao.update(approval);
    }

    @Override
    public List<Approval> queryByState(String state) {
        return approvalDao.queryByState(state);
    }

    @Override
    public List<Approval> queryByType(String type) {
        return approvalDao.queryByType(type);
    }

    @Override
    public List<Approval> queryByHostel(String hostelNum) {
        return approvalDao.queryByHostel(hostelNum);
    }

    @Override
    public List<Approval> getAllList() {
        return approvalDao.getAllList();
    }
}
