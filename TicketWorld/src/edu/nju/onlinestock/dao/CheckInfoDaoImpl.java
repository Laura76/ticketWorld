package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.CheckInfoDao;
import edu.nju.onlinestock.model.CheckInfo;

import java.util.ArrayList;
import java.util.List;

public class CheckInfoDaoImpl extends BaseDaoImpl implements CheckInfoDao {

    private BaseDao baseDao;
    private static CheckInfoDaoImpl checkInfoDao = new CheckInfoDaoImpl();

	private CheckInfoDaoImpl() {

	}

	public static CheckInfoDaoImpl getInstance() {
		return checkInfoDao;
	}

    @Override
    public void save(CheckInfo checkInfo) {
        super.save(checkInfo);
    }

    @Override
    public void update(CheckInfo checkInfo) {
        super.update(checkInfo);
    }

    @Override
    public CheckInfo find(int checkNum) {
        CheckInfo checkInfo=(CheckInfo) super.load(CheckInfo.class,checkNum);
        return checkInfo;
    }

    @Override
    public List<CheckInfo> getCheckInfoByHostel(String hostelNum) {
        String sql="select * from hostelworld.checkInfo as ci where ci.hostelNum=\""+hostelNum+"\";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCheckInfo(objects);
    }

    @Override
    public List<CheckInfo> getCheckInfoByHostelAndLodger(String hostelNum, String lodgerNum) {
        String sql="select * from hostelworld.checkInfo as ci where ci.hostelNum=\""+hostelNum+
                "\" and lodgerNum=\""+lodgerNum+"\";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCheckInfo(objects);
    }

    @Override
    public List<CheckInfo> getCheckInfoByCheckin(String hostelNum,String checkinDate) {
        String sql="select * from hostelworld.checkInfo as ci where ci.hostelNum=\""+hostelNum+
                "\" and checkinDate=\""+checkinDate+"\";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCheckInfo(objects);
    }

    @Override
    public List<CheckInfo> getCheckInfoBycCheckout(String hostelNum,String checkoutDate) {
        String sql="select * from hostelworld.checkInfo as ci where ci.hostelNum=\""+hostelNum+
                "\" and checkoutDate=\""+checkoutDate+"\";";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCheckInfo(objects);
    }

    @Override
    public List<CheckInfo> getCheckInfoBetween(String startDate, String endDate) {
        String sql="select * from hostelworld.checkInfo as ci where UNIX_TIMESTAMP(ci.checkinDate)<UNIX_TIMESTAMP(\"" +
                endDate +
                "\") AND " +
                "UNIX_TIMESTAMP(ci.checkoutDate)>UNIX_TIMESTAMP(\"" +
                startDate +
                "\");";
        List<Object[]> objects=super.querySQL(sql);
        return this.getCheckInfo(objects);
    }

    private List<CheckInfo> getCheckInfo(List<Object[]> objects){
        List<CheckInfo> checkInfos=new ArrayList<>();
        for(Object[] object:objects){
            CheckInfo checkInfo=new CheckInfo();
            checkInfo.setHostelNum(String.valueOf(object[4]));
            checkInfo.setLodgerName(String.valueOf(object[5]));
            checkInfo.setPaidMoney((double)object[7]);
            checkInfo.setCheckCondition(String.valueOf(object[1]));
            checkInfo.setCheckinDate(String.valueOf(object[2]));
            checkInfo.setCheckoutDate(String.valueOf(object[3]));
            checkInfo.setCheckNum((int)object[0]);
            checkInfo.setRoomTypeId((int)object[9]);
            checkInfo.setRoomNum(String.valueOf(object[8]));
            checkInfo.setOrderNum(String.valueOf(object[6]));
            checkInfos.add(checkInfo);
        }
        return checkInfos;
    }
}
