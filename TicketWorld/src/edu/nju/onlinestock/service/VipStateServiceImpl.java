package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.Vip;
import edu.nju.onlinestock.service.VipStateService;
import util.VipStateEnum;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VipStateServiceImpl implements VipStateService {

    private VipDao vipDao=DaoFactory.getVipDao();
    private static VipStateServiceImpl vipStateService=new VipStateServiceImpl();
    /**
     * Default constructor. 
     */	
	private VipStateServiceImpl()
	{
		
	}
	
	public static VipStateService getInstance()
	{
		return vipStateService;
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
    public boolean changeState() {
        //获得所有会员
        List<Vip> vips=vipDao.getAllVipList();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获得当前日期
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        //判断哪些会员到有效期了且卡上费用小于1000的
        for(Vip vip:vips){
            if(vip.getState().equals(VipStateEnum.ACTIVATE.toString())) {
                try {
                    Date validDate = sdf.parse(vip.getValidDate());
                    //判断今天是否是激活态会员卡的到期日
                    if(!date.before(validDate)) {
                        //已经到有效期限
                        if(vip.getMoney()<1000) {
                            //会员余额不足，会员卡转为暂停状态,有效期延后一年
                            calendar.add(Calendar.YEAR,1);
                            vip.setState(VipStateEnum.SUSPEND.toString());
                            vip.setValidDate(sdf.format(calendar.getTime()));
                            vipDao.update(vip);
                        }else {
                            //会员余额充足，将有效期延后一年
                            calendar.add(Calendar.YEAR,1);
                            vip.setValidDate(sdf.format(calendar.getTime()));
                            vipDao.update(vip);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }

            }else if(vip.getState().equals(VipStateEnum.SUSPEND.toString())){
                try {
                    Date validDate=sdf.parse(vip.getValidDate());
                    //判断是否是暂停态会员卡的到期日
                    if(!date.before(validDate)){
                        //已经到了暂停日子的有效期限,停止该会员卡
                        vip.setState(VipStateEnum.CANCEL.toString());
                        vipDao.update(vip);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }


}
