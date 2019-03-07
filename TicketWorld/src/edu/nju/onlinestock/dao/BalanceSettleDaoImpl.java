package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BalanceSettleDao;
import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.model.BalanceSettle;

import java.util.ArrayList;
import java.util.List;

public class BalanceSettleDaoImpl extends BaseDaoImpl implements BalanceSettleDao {

    private BaseDao baseDao;
    private static BalanceSettleDaoImpl balanceSettleDao = new BalanceSettleDaoImpl();

	private BalanceSettleDaoImpl() {

	}

	public static BalanceSettleDaoImpl getInstance() {
		return balanceSettleDao;
	}
    @Override
    public void save(BalanceSettle balanceSettle) {
        super.save(balanceSettle);
    }

    @Override
    public List<BalanceSettle> getAll() {
        return super.getAllList(BalanceSettle.class);
//        String sql="select * from hostelworld.balanceSettle;";
//        List<Object[]> objects=baseDao.querySQL(sql);
//        return this.getBalanceSettle(objects);
    }

    @Override
    public void update(BalanceSettle balanceSettle) {
    		super.update(balanceSettle);
    }


    @Override
    public List<BalanceSettle> getBalanceSettleByDate(String date) {
        String sql="select * from hostelworld.balanceSettle as bs where bs.settleDate='"+date+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getBalanceSettle(objects);
    }

    @Override
    public List<BalanceSettle> getBalanceSettleByHostel(String hostelNum) {
        String sql="select * from hostelworld.balanceSettle as bs where bs.hostelNum='"+hostelNum+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getBalanceSettle(objects);
    }

    @Override
    public List<BalanceSettle> getBalanceSettleByCondition(String condition) {
        String sql="select * from hostelworld.balanceSettle as bs where bs.settleCondition='"+condition+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getBalanceSettle(objects);
    }

    @Override
    public BalanceSettle getByHostelAndCondition(String hostelNum, String condition) {
        String sql="select * from hostelworld.balanceSettle as bs where bs.settleCondition='"+condition+"' and hostelNum='" +hostelNum
                +"';";
        List<Object[]> objects=super.querySQL(sql);
        List<BalanceSettle> balanceSettles=this.getBalanceSettle(objects);
        if(balanceSettles.size()>0){
            return balanceSettles.get(0);
        }else{
            return null;
        }

    }

    private List<BalanceSettle> getBalanceSettle(List<Object[]> objects){
        List<BalanceSettle> balanceSettles=new ArrayList<>();
        for(Object[] object:objects){
            BalanceSettle balanceSettle=new BalanceSettle();
            balanceSettle.setSettleNum((int)object[0]);
            balanceSettle.setSettleDate(String.valueOf(object[4]));
            balanceSettle.setBalance((double)object[1]);
            balanceSettle.setSettleCondition(String.valueOf(object[3]));
            balanceSettle.setHostelNum(String.valueOf(object[2]));
            balanceSettles.add(balanceSettle);
        }
        return balanceSettles;
    }
}
