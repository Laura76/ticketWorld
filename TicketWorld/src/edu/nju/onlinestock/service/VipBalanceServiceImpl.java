package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.BankCardDao;
import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.BankCard;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.VipBalanceService;
import util.VipStateEnum;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VipBalanceServiceImpl implements VipBalanceService {

    private VipDao vipDao=DaoFactory.getVipDao();
    private BankCardDao bankCardDao=DaoFactory.getBankCardDao();
    private static VipBalanceServiceImpl vipBalanceService=new VipBalanceServiceImpl();
    /**
     * Default constructor. 
     */	
	private VipBalanceServiceImpl()
	{
		
	}
	
	public static VipBalanceService getInstance()
	{
		return vipBalanceService;
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
    public boolean activateVip(String vipNum, double money) {

        Vip vip = vipDao.find(vipNum);
        BankCard bankCard=bankCardDao.find(vip.getBankCardId());
        double balance=bankCard.getBalance();
        if(vip!=null&&null!=bankCard){
            if(balance>=1000&&money>=1000) {
                double currentMoney = vip.getMoney();
                vip.setMoney(currentMoney + money);
                vip.setState(VipStateEnum.ACTIVATE.toString());
                //获得当前日期
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = calendar.getTime();
                // 设置激活日期
                vip.setActivateDate(sdf.format(date));
                //设置有效日期
                calendar.add(Calendar.YEAR, 1);
                date = calendar.getTime();
                vip.setValidDate(sdf.format(date));
                vipDao.update(vip);
                bankCard.setBalance(balance-money);
                bankCardDao.update(bankCard);

                return true;
            }
        }
        return false;
    }

    @Override
    public int addCredit(String vipNum, double money) {
        Vip vip=vipDao.find(vipNum);
        if(null!=vip){
            double currentPoint=vip.getVipPoint();
            vip.setVipPoint(currentPoint+money);
            vipDao.update(vip);
            int vipLevel=this.getVipLevel(currentPoint+money);
            return vipLevel;
        }
        return -1;
    }

    @Override
    public double convertCreditToMoney(String vipNum, double point) {
        Vip vip=vipDao.find(vipNum);
        if(null!=vip){
            double currentMoney=vip.getMoney();
            double currentPoint=vip.getVipPoint();
            double money=point/10;
            if(currentPoint>point) {
                vip.setMoney(currentMoney + money);
                vip.setVipPoint(currentPoint - point);
                vipDao.update(vip);
                return currentMoney+money;
            }else{
                return -1;
            }
        }
        return -1;
    }

//    @Override
    public int getVipLevel(double point) {
        int[] scale={0,500,1000,2000,5000};
        int[] levels={0,1,2,3,4,5};
        int level=0;
        while(point>scale[level]){
            level++;
        }
        return levels[level];
    }

    @Override
    public boolean topUp(String vipNum, double money) {
        Vip vip=vipDao.find(vipNum);
        BankCard bankCard=bankCardDao.find(vip.getBankCardId());
        if(null!=vip && null!=bankCard){
            double currentBalance=bankCard.getBalance();
            System.out.println("balance="+currentBalance);
            if(currentBalance>=money) {
                if (vip.getState().equals(VipStateEnum.SUSPEND.toString())) {
                    //暂停态会员
                    bankCard.setBalance(currentBalance-money);
                    bankCardDao.update(bankCard);
                    double currentMoney = vip.getMoney();
                    vip.setMoney(currentMoney + money);
                    if ((currentMoney + money) >= 1000) {
                        vip.setState(VipStateEnum.ACTIVATE.toString());
                        //获得有效期
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.YEAR, 1);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = calendar.getTime();
                        vip.setValidDate(sdf.format(date));
                        vipDao.update(vip);
                        return true;
                    }
                } else if (vip.getState().equals(VipStateEnum.ACTIVATE.toString())) {
                    //激活态会员
                    System.out.println("ACTIVATE");
                    bankCard.setBalance(currentBalance-money);
                    bankCardDao.update(bankCard);
                    double currentMoney = vip.getMoney();
                    vip.setMoney(currentMoney + money);
                    vipDao.update(vip);
                    return true;
                } else {
                    return false;
                }
            }
        }
      return false;
    }

    @Override
    public double withdraw(String vipNum, double money) {
        Vip vip=vipDao.find(vipNum);
        if(null!=vip){
            if(vip.getState().equals(VipStateEnum.ACTIVATE.toString())) {
                double currentMoney = vip.getMoney();
                if (currentMoney >= money) {
                		int vipLevel=this.addCredit(vipNum,money);
                		vip=vipDao.find(vipNum);
                    vip.setMoney(currentMoney - money);
                    vip.setVipLevel(vipLevel);
                    vipDao.update(vip);
                    return vipLevel;
                } else {
                    return -1;
                }
            }
        }
        return -1;
    }
}
