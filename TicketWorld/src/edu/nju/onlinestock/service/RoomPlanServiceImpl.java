package edu.nju.onlinestock.service;

import edu.nju.onlinestock.dao.RoomPlanDao;
import edu.nju.onlinestock.dao.RoomTypeDao;
import edu.nju.onlinestock.factory.DaoFactory;
import edu.nju.onlinestock.model.RoomType;
import edu.nju.onlinestock.model.RoomPlan;
import edu.nju.onlinestock.service.RoomPlanService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomPlanServiceImpl implements RoomPlanService {
    private RoomPlanDao roomPlanDao=DaoFactory.getRoomPlanDao();
    private RoomTypeDao roomTypeDao=DaoFactory.getRoomTypeDao();
    private static RoomPlanServiceImpl roomPlanService=new RoomPlanServiceImpl();
    /**
     * Default constructor. 
     */	
	private RoomPlanServiceImpl()
	{
		
	}
	
	public static RoomPlanService getInstance()
	{
		return roomPlanService;
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
    public void saveRoomPlan(RoomPlan roomPlan) {
        roomPlanDao.save(roomPlan);
    }

    @Override
    public void update(RoomPlan roomPlan) {
        roomPlanDao.update(roomPlan);
    }

    @Override
    public long getNextId() {
        return roomPlanDao.getNextId();
    }

    @Override
    public List<RoomPlan> queryByHostel(String hostelNum) {
        return roomPlanDao.queryByHostel(hostelNum);
    }

    @Override
    public List<RoomPlan> queryNewestRoomPlan(String hostelNum) {
        List<RoomType> roomTypes = roomTypeDao.getAllType();
        List<RoomPlan> roomPlans=new ArrayList<>();
        for(RoomType roomType : roomTypes){
            String type= roomType.getRoomType();
            RoomPlan roomPlan=this.queryNewestPlanByType(hostelNum,type);
            roomPlans.add(roomPlan);
        }
        return roomPlans;
    }

    @Override
    public RoomPlan queryNewestPlanByType(String hostelNum, String roomType) {
        int roomTypeId= roomTypeDao.queryByType(roomType).getId();
        System.out.println(roomTypeId);
        List<RoomPlan> roomPlans=roomPlanDao.queryByHostelAndType(hostelNum,roomTypeId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        RoomPlan newestPlan=roomPlans.get(0);
        try {
            Date date=sdf.parse(roomPlans.get(0).getDate());
            for(RoomPlan roomPlan:roomPlans){
                Date planDate=sdf.parse(roomPlan.getDate());
                if(planDate.after(date)){
                    date=planDate;
                    newestPlan=roomPlan;
                }
            }
            return newestPlan;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
