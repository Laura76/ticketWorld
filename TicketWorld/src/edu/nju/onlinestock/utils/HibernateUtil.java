package edu.nju.onlinestock.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import edu.nju.onlinestock.model.Approval;
import edu.nju.onlinestock.model.BalanceSettle;
import edu.nju.onlinestock.model.BankCard;
import edu.nju.onlinestock.model.CheckInfo;
import edu.nju.onlinestock.model.CurrentSpareRoomInfo;
import edu.nju.onlinestock.model.DiscountStrategy;
import edu.nju.onlinestock.model.Hostel;
import edu.nju.onlinestock.model.LodgerInfo;
import edu.nju.onlinestock.model.Manager;
import edu.nju.onlinestock.model.Orders;
import edu.nju.onlinestock.model.RoomPlan;
import edu.nju.onlinestock.model.RoomType;
import edu.nju.onlinestock.model.Vip;


public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	 public static SessionFactory getSessionFactory(){
		 try {
				Configuration config;
				ServiceRegistry serviceRegistry;
				config = new Configuration().configure();
				config.addAnnotatedClass(Approval.class);
				config.addAnnotatedClass(BalanceSettle.class);
				config.addAnnotatedClass(BankCard.class);
				config.addAnnotatedClass(CheckInfo.class);
				config.addAnnotatedClass(CurrentSpareRoomInfo.class);
				config.addAnnotatedClass(DiscountStrategy.class);
				config.addAnnotatedClass(Hostel.class);
				config.addAnnotatedClass(LodgerInfo.class);
				config.addAnnotatedClass(Manager.class);
				config.addAnnotatedClass(Orders.class);
				config.addAnnotatedClass(RoomPlan.class);
				config.addAnnotatedClass(RoomType.class);
				config.addAnnotatedClass(Vip.class);
				
				serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
				sessionFactory=config.buildSessionFactory(serviceRegistry);	
				return sessionFactory;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	 }
	 
	 /** * gerCurrentSession ���Զ��ر�session��ʹ�õ��ǵ�ǰ��session���� * * @return */
	 public static Session getSession(){
		 return getSessionFactory().getCurrentSession();
	 }
}
