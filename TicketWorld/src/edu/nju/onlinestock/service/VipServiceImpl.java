package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.dao.BankCardDao;
import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.BankCard;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.VipService;
import util.VipStateEnum;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VipServiceImpl implements VipService{

    private static VipDao vipDao=DaoFactory.getVipDao();
    private static BankCardDao bankCardDao=DaoFactory.getBankCardDao();
	
	private static VipServiceImpl vipService=new VipServiceImpl();
    /**
     * Default constructor. 
     */	
	private VipServiceImpl()
	{
		
	}
	
	public static VipService getInstance()
	{
		return vipService;
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
    public boolean isExist(String vipNum) {
        return vipDao.checkVip(vipNum);
    }

    @Override
    public boolean checkPassword(String vipNum, String password) {
        return vipDao.checkPassword(vipNum,password);
    }

    @Override
    public void registerVip(Vip vip) {
        vipDao.save(vip);
        String bankCardId=vip.getBankCardId();

        BankCard bankCard=new BankCard();
        bankCard.setBankCardId(bankCardId);
        bankCard.setBalance(10000);
        bankCardDao.save(bankCard);
    }

    @Override
    public String getVipNum() {
        return vipDao.getVipNum();
    }

    @Override
    public boolean cancelVip(String vipNum) {
        Vip vip=vipDao.find(vipNum);
        vip.setState(VipStateEnum.CANCEL.toString());
        vipDao.update(vip);
        return true;
    }

    @Override
    public void updateVip(Vip vip) {
        String bankCardId=vip.getBankCardId();
        if(bankCardDao.find(bankCardId)==null) {
            BankCard bankCard = new BankCard();
            bankCard.setBankCardId(bankCardId);
            bankCard.setBalance(10000);
            bankCardDao.save(bankCard);
        }
        vipDao.update(vip);
    }

    @Override
    public Vip findVipById(String vipNum) {
        return vipDao.find(vipNum);
    }

    @Override
    public List<Vip> queryByName(String vipName) {
        return vipDao.queryByName(vipName);
    }

    @Override
    public List<Vip> getAllVipList() {
        return vipDao.getAllVipList();
    }
}
