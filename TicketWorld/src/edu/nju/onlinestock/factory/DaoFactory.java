package edu.nju.onlinestock.factory;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.dao.ApprovalDaoImpl;
import edu.nju.onlinestock.dao.BalanceSettleDao;
import edu.nju.onlinestock.dao.BalanceSettleDaoImpl;
import edu.nju.onlinestock.dao.BankCardDao;
import edu.nju.onlinestock.dao.BankCardDaoImpl;
import edu.nju.onlinestock.dao.CheckInfoDao;
import edu.nju.onlinestock.dao.CheckInfoDaoImpl;
import edu.nju.onlinestock.dao.CurrentSpareRoomInfoDao;
import edu.nju.onlinestock.dao.CurrentSpareRoomInfoDaoImpl;
import edu.nju.onlinestock.dao.DiscountStrategyDao;
import edu.nju.onlinestock.dao.DiscountStrategyDaoImpl;
import edu.nju.onlinestock.dao.HostelDao;
import edu.nju.onlinestock.dao.HostelDaoImpl;
import edu.nju.onlinestock.dao.LodgerInfoDao;
import edu.nju.onlinestock.dao.LodgerInfoDaoImpl;
import edu.nju.onlinestock.dao.ManagerDao;
import edu.nju.onlinestock.dao.ManagerDaoImpl;
import edu.nju.onlinestock.dao.OrdersDao;
import edu.nju.onlinestock.dao.OrdersDaoImpl;
import edu.nju.onlinestock.dao.RoomPlanDao;
import edu.nju.onlinestock.dao.RoomPlanDaoImpl;
import edu.nju.onlinestock.dao.RoomTypeDao;
import edu.nju.onlinestock.dao.RoomTypeDaoImpl;
import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.dao.VipDaoImpl;


public class DaoFactory {
	public static ApprovalDao getApprovalDao()
	{
		return ApprovalDaoImpl.getInstance();
	}
	public static LodgerInfoDao getLodgerInfoDao()
	{
		return LodgerInfoDaoImpl.getInstance();
	}
	public static RoomPlanDao getRoomPlanDao()
	{
		return RoomPlanDaoImpl.getInstance();
	}
	public static CheckInfoDao getCheckInfoDao()
	{
		return CheckInfoDaoImpl.getInstance();
	}
	public static CurrentSpareRoomInfoDao getCurrentSpareRoomInfoDao()
	{
		return CurrentSpareRoomInfoDaoImpl.getInstance();
	}
	public static DiscountStrategyDao getDiscountStrategyDao()
	{
		return DiscountStrategyDaoImpl.getInstance();
	}
	public static HostelDao getHostelDao()
	{
		return HostelDaoImpl.getInstance();
	}
	public static RoomTypeDao getRoomTypeDao()
	{
		return RoomTypeDaoImpl.getInstance();
	}
	public static BalanceSettleDao getBalanceSettleDao()
	{
		return BalanceSettleDaoImpl.getInstance();
	}
	public static ManagerDao getManagerDao()
	{
		return ManagerDaoImpl.getInstance();
	}
	public static OrdersDao getOrdersDao()
	{
		return OrdersDaoImpl.getInstance();
	}
	public static BankCardDao getBankCardDao()
	{
		return BankCardDaoImpl.getInstance();
	}
	public static VipDao getVipDao()
	{
		return VipDaoImpl.getInstance();
	}
}
