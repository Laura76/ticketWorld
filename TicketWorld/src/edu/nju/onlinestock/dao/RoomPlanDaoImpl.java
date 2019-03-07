package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.RoomPlanDao;
import edu.nju.onlinestock.model.RoomPlan;

import java.util.ArrayList;
import java.util.List;

public class RoomPlanDaoImpl extends BaseDaoImpl implements RoomPlanDao {
    private BaseDao baseDao;

    private static RoomPlanDaoImpl roomPlanDao = new RoomPlanDaoImpl();

	private RoomPlanDaoImpl() {

	}

	public static RoomPlanDaoImpl getInstance() {
		return roomPlanDao;
	}
//    public void setBaseDao(BaseDao baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }

    @Override
    public void save(RoomPlan roomPlan) {
        super.save(roomPlan);
    }

    @Override
    public void update(RoomPlan roomPlan) {
        super.update(roomPlan);
    }

    @Override
    public long getNextId() {
        return super.getTotalCount(RoomPlan.class)+1;
    }

    @Override
    public List<RoomPlan> queryByHostel(String hostelNum) {
        String sql="select * from hostelworld.roomPlan as ri where ri.hostelNum=\""+hostelNum+"\";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getRoomPlan(objects);
    }

    @Override
    public List<RoomPlan> queryByHostelAndType(String hostelNum, int roomTypeId) {
        String sql="select * from hostelworld.roomPlan as ri where ri.hostelNum=\""+hostelNum+"\" and roomTypeId="+roomTypeId+";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getRoomPlan(objects);
    }

    private List<RoomPlan> getRoomPlan(List<Object[]> objects){
        List<RoomPlan> roomPlans=new ArrayList<>();
        for(Object[] object:objects){
            RoomPlan roomPlan=new RoomPlan();
            roomPlan.setId((int)object[0]);
            roomPlan.setHostelNum(String.valueOf(object[3]));
            roomPlan.setRoomTypeId((int)object[6]);
            roomPlan.setRoomNum((int)object[4]);
            roomPlan.setStartDate(String.valueOf(object[9]));
            roomPlan.setEndDate(String.valueOf(object[2]));
            roomPlan.setRoomPrice((double)object[5]);
            roomPlan.setDate(String.valueOf(object[1]));
            roomPlan.setShowInfo(String.valueOf(object[7]));
            roomPlan.setShowType(String.valueOf(object[8]));
            roomPlans.add(roomPlan);
        }
        return roomPlans;
    }
}
