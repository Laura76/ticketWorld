package edu.nju.onlinestock.factory;

import edu.nju.onlinestock.service.ApprovalServiceImpl;
import edu.nju.onlinestock.service.BalanceSettleService;
import edu.nju.onlinestock.service.BalanceSettleServiceImpl;
import edu.nju.onlinestock.service.CheckInfoService;
import edu.nju.onlinestock.service.CheckInfoServiceImpl;
import edu.nju.onlinestock.service.CurrentSpareRoomService;
import edu.nju.onlinestock.service.CurrentSpareRoomServiceImpl;
import edu.nju.onlinestock.service.DiscountStrategyService;
import edu.nju.onlinestock.service.DiscountStrategyServiceImpl;
import edu.nju.onlinestock.service.HostelServiceImpl;
import edu.nju.onlinestock.service.ManagerServiceImpl;
import edu.nju.onlinestock.service.OrdersService;
import edu.nju.onlinestock.service.OrdersServiceImpl;
import edu.nju.onlinestock.service.RoomPlanService;
import edu.nju.onlinestock.service.RoomPlanServiceImpl;
import edu.nju.onlinestock.service.RoomTypeService;
import edu.nju.onlinestock.service.RoomTypeServiceImpl;
import edu.nju.onlinestock.service.VipBalanceService;
import edu.nju.onlinestock.service.VipBalanceServiceImpl;
import edu.nju.onlinestock.service.ManagerService;
import edu.nju.onlinestock.service.HostelService;
import edu.nju.onlinestock.service.ApprovalService;
import edu.nju.onlinestock.service.VipServiceImpl;
import edu.nju.onlinestock.service.VipStateService;
import edu.nju.onlinestock.service.VipStateServiceImpl;
import edu.nju.onlinestock.service.VipService;


public class ServiceFactory {
	public static VipStateService getVipStateService()
	{
		return VipStateServiceImpl.getInstance();
	}
	public static RoomPlanService getRoomPlanService()
	{
		return RoomPlanServiceImpl.getInstance();
	}
	public static RoomTypeService getRoomTypeService()
	{
		return RoomTypeServiceImpl.getInstance();
	}
	public static DiscountStrategyService getDiscountStrategyService()
	{
		return DiscountStrategyServiceImpl.getInstance();
	}
	public static CheckInfoService getCheckInfoService()
	{
		return CheckInfoServiceImpl.getInstance();
	}
	public static BalanceSettleService getBalanceSettleService()
	{
		return BalanceSettleServiceImpl.getInstance();
	}
	public static ApprovalService getApprovalService()
	{
		return ApprovalServiceImpl.getInstance();
	}
	public static HostelService getHostelService()
	{
		return HostelServiceImpl.getInstance();
	}
	public static ManagerService getManagerService()
	{
		return ManagerServiceImpl.getInstance();
	}
	public static VipService getVipService()
	{
		return VipServiceImpl.getInstance();
	}
	public static CurrentSpareRoomService getCurrentSpareRoomService()
	{
		return CurrentSpareRoomServiceImpl.getInstance();
	}
	public static OrdersService getOrdersService()
	{
		return OrdersServiceImpl.getInstance();
	}
	public static VipBalanceService getVipBalanceService()
	{
		return VipBalanceServiceImpl.getInstance();
	}
}
