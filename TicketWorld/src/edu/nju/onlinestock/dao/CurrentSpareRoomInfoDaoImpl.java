package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.CurrentSpareRoomInfoDao;
import edu.nju.onlinestock.model.CurrentSpareRoomInfo;

import java.util.ArrayList;
import java.util.List;

public class CurrentSpareRoomInfoDaoImpl extends BaseDaoImpl implements CurrentSpareRoomInfoDao {

    private BaseDao baseDao;

    private static CurrentSpareRoomInfoDaoImpl currentSpareRoomInfoDao = new CurrentSpareRoomInfoDaoImpl();

	private CurrentSpareRoomInfoDaoImpl() {

	}

	public static CurrentSpareRoomInfoDaoImpl getInstance() {
		return currentSpareRoomInfoDao;
	}
//    public void setBaseDao(BaseDao baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }

    @Override
    public void save(CurrentSpareRoomInfo currentSpareRoomInfo) {
        super.save(currentSpareRoomInfo);
    }

    @Override
    public long getNextId() {
        return super.getTotalCount(CurrentSpareRoomInfo.class)+1;
    }

    @Override
    public void update(CurrentSpareRoomInfo currentSpareRoomInfo) {
        super.update(currentSpareRoomInfo);
    }

    @Override
    public List<CurrentSpareRoomInfo> getInfoByHostel(String hostelNum) {
        String sql="select * from hostelworld.currentSpareRoomInfo as csri where csri.hostelNum='"+hostelNum+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCurrentSpareRoomInfo(objects);
    }

    @Override
    public CurrentSpareRoomInfo getInfoByHostelAndRoomType(String hostelNum, int roomTypeId) {
        String sql="select * from hostelworld.currentSpareRoomInfo as csri where csri.hostelNum='"+hostelNum+"' and csri.roomTypeId="+roomTypeId+";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCurrentSpareRoomInfo(objects).get(0);
    }

    private List<CurrentSpareRoomInfo> getCurrentSpareRoomInfo(List<Object[]> objects){
        List<CurrentSpareRoomInfo> currentSpareRoomInfos=new ArrayList<>();
        for(Object[] object:objects){
            CurrentSpareRoomInfo currentSpareRoomInfo=new CurrentSpareRoomInfo();
            currentSpareRoomInfo.setHostelNum(String.valueOf(object[1]));
            currentSpareRoomInfo.setSpareNum((int)object[3]);
            currentSpareRoomInfo.setId((int)object[0]);
            currentSpareRoomInfo.setRoomTypeId((int)object[2]);
            currentSpareRoomInfos.add(currentSpareRoomInfo);
        }
        return currentSpareRoomInfos;
    }
}
