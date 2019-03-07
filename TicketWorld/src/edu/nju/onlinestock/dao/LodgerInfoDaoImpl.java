package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.LodgerInfoDao;
import edu.nju.onlinestock.model.LodgerInfo;

import java.text.DecimalFormat;
import java.util.List;

public class LodgerInfoDaoImpl extends BaseDaoImpl implements LodgerInfoDao {
    private BaseDao baseDao;

    private static LodgerInfoDaoImpl lodgerInfoDao = new LodgerInfoDaoImpl();

	private LodgerInfoDaoImpl() {

	}

	public static LodgerInfoDaoImpl getInstance() {
		return lodgerInfoDao;
	}

//    public void setBaseDao(BaseDao baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }

    @Override
    public void save(LodgerInfo lodgerInfo) {
        super.save(lodgerInfo);
    }

    @Override
    public String getLodgerNum() {
        long currentNum=super.getTotalCount(LodgerInfo.class);
        DecimalFormat df=new DecimalFormat("000000");
        String newNum="N"+df.format(currentNum+1);
        return newNum;
    }


    @Override
    public void update(LodgerInfo lodgerInfo) {
        super.update(lodgerInfo);
    }

    @Override
    public LodgerInfo find(String lodgerNum) {
        LodgerInfo lodgerInfo=(LodgerInfo)super.load(LodgerInfo.class,lodgerNum);
        return lodgerInfo;
    }

    @Override
    public LodgerInfo queryByName(String lodgerName) {
        String sql="select * from hostelworld.lodgerInfo li where li.lodgerName=\""+lodgerName+"\";";
        List<LodgerInfo> lodgerInfos=super.querySQL(sql);
        return lodgerInfos.get(0);
    }

    @Override
    public List<LodgerInfo> getAllLodgerInfo() {
        return super.getAllList(LodgerInfo.class);
    }



}
