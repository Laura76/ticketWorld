package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.OrdersDao;
import edu.nju.onlinestock.model.Orders;
import util.OrderConditionEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class OrdersDaoImpl extends BaseDaoImpl implements OrdersDao {
    private BaseDao baseDao;
    private static OrdersDaoImpl ordersDao = new OrdersDaoImpl();

	private OrdersDaoImpl() {

	}

	public static OrdersDaoImpl getInstance() {
		return ordersDao;
	}

//    public void setBaseDao(BaseDao baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }


    @Override
    public void save(Orders orders) {
        super.save(orders);
    }

    @Override
    public String getOrderNum(String hostelNum) {
        int currentNum=this.queryByHostel(hostelNum).size();
        DecimalFormat df=new DecimalFormat("000000");
        String newNum=hostelNum+df.format(currentNum+1);
        return newNum;
    }

    @Override
    public void update(Orders orders) {
    		super.update(orders);
    }

    @Override
    public Orders find(String orderNum) {
        Orders orders=(Orders) super.load(Orders.class,orderNum);
        return orders;
    }

    @Override
    public List<Orders> queryByHostel(String hostelNum) {
        String sql="select * from hostelworld.orders as o where o.hostelNum=\""+hostelNum+"\";";
        List<Object[]> orderses=super.querySQL(sql);
        return this.getOrders(orderses);
    }

    @Override
    public List<Orders> queryByVip(String vipNum) {
        String sql="select * from hostelworld.orders as o where o.vipNum=\""+vipNum+"\";";
        List<Object[]> orderses=super.querySQL(sql);
        return this.getOrders(orderses);
    }

    @Override
    public List<Orders> queryByHostelAndCheckin(String hostelNum, String checkinDate) {
        String sql="select * from hostelworld.orders as o where o.hostelNum=\""+hostelNum+"\" and o.checkinDate=\""+checkinDate+"\";";
        List<Object[]> orderses=super.querySQL(sql);
        return this.getOrders(orderses);
    }

    @Override
    public List<Orders> queryByHostelAndCheckout(String hostelNum, String checkoutDate) {
        String sql="select * from hostelworld.orders as o where o.hostelNum=\""+hostelNum+"\" and o.checkoutDate=\""+checkoutDate+"\" and o.orderCondition=\"" +
                OrderConditionEnum.CHECKOUT+"\";";
        List<Object[]> orderses=super.querySQL(sql);
        return this.getOrders(orderses);
    }

    @Override
    public List<Orders> queryByCondition(String orderCondition) {
        String sql="select * from hostelworld.orders as o where o.orderCondition=\"" +
                orderCondition+"\";";
        List<Object[]> orderses=super.querySQL(sql);
        return this.getOrders(orderses);
    }

    private List<Orders> getOrders(List<Object[]> objects){
        List<Orders> ordersList=new ArrayList<>();
        for(Object[] object:objects){
            Orders orders=new Orders();
            orders.setOrderNum((String.valueOf(object[0])));
            orders.setHostelNum(String.valueOf(object[3]));
            orders.setVipNum(String.valueOf(object[10]));
            orders.setRoomTypeId((int) object[9]);
            orders.setRoomNum((int)object[8]);
            orders.setRequiredMoney((double)object[7]);
            orders.setPaidMoney((double)object[5]);
            orders.setOrderCondition(String.valueOf(object[4]));
            orders.setCheckinDate(String.valueOf(object[1]));
            orders.setCheckoutDate(String.valueOf(object[2]));
            orders.setPayMethod(String.valueOf(object[6]));
            orders.setOrderTime(String.valueOf(object[11]));
            orders.setSeatNum(String.valueOf(object[12]));
            ordersList.add(orders);
        }
        return ordersList;
    }
}
