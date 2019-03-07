package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.VipDao;
import edu.nju.onlinestock.model.Vip;

import java.text.DecimalFormat;
import java.util.List;

public class VipDaoImpl extends BaseDaoImpl implements VipDao {

    private BaseDao baseDao;

    private static VipDaoImpl vipDao = new VipDaoImpl();

	private VipDaoImpl() {

	}

	public static VipDaoImpl getInstance() {
		return vipDao;
	}
//    public void setBaseDao(BaseDao baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }

    @Override
    public void save(Vip vip) {
        try {
            super.save(vip);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getVipNum() {
        long currentNum=super.getTotalCount(Vip.class);
        DecimalFormat df=new DecimalFormat("000000");
        String newNum="V"+df.format(currentNum+1);
        return newNum;
    }

    @Override
    public void update(Vip vip) {
        super.update(vip);
    }

    @Override
    public Vip find(String vipNum) {
        Vip vip=(Vip)super.load(Vip.class,vipNum);
        return vip;
    }

    @Override
    public boolean checkVip(String vipNum) {
        if(this.find(vipNum)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPassword(String vipNum, String password) {
        Vip vip=this.find(vipNum);
        if(vip.getVipPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public List<Vip> queryByName(String vipName) {
        String sql="select * from hostelworld.vip v where v.vipName=\""+vipName+"\";";
        return super.querySQL(sql);
    }

    @Override
    public List<Vip> getAllVipList() {
        List<Vip> list=super.getAllList(Vip.class);
        return list;
    }
}
