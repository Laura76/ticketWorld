package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.dao.HostelDao;
import edu.nju.onlinestock.model.Hostel;
import util.ApprovalStateEnum;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HostelDaoImpl extends BaseDaoImpl implements HostelDao {

    private BaseDao baseDao;
    
    private static HostelDaoImpl hostelDao = new HostelDaoImpl();

	private HostelDaoImpl() {

	}

	public static HostelDaoImpl getInstance() {
		return hostelDao;
	}

//    public void setBaseDao(BaseDaoImpl baseDao) {
//        this.baseDao = baseDao;
//    }
//
//    public BaseDao getBaseDao() {
//        return baseDao;
//    }

    @Override
    public boolean checkHostel(String hostelNum) {
        if(null!=this.queryHostelByNum(hostelNum)){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkApprove(String hostelNum) {
        Hostel hostel=this.queryHostelByNum(hostelNum);
        if(hostel.getApprovalState().equals(ApprovalStateEnum.APPROVE.toString())){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPassword(String hostelNum, String hostelPassword) {
        Hostel hostel=this.queryHostelByNum(hostelNum);
        if(hostel.getHostelPassword().equals(hostelPassword)){
            return true;
        }
        return false;
    }

    @Override
    public void save(Hostel hostel) {
        super.save(hostel);
    }

    @Override
    public String getHostelNum() {
        long currentNum=super.getTotalCount(Hostel.class);
        DecimalFormat df=new DecimalFormat("000000");
        String newNum="H"+df.format(currentNum+1);
        return newNum;
    }

    @Override
    public void deleteHostel(String hostelNum) {
        super.delete(Hostel.class,hostelNum);
    }

    @Override
    public void updateHostel(Hostel hostel) {
        super.update(hostel);
    }

    @Override
    public List<Hostel> queryHostelByProvince(String province) {
        String sql="select * from hostelworld.hostel as h where h.provice='"+province+"';";
        List<Hostel> list=super.querySQL(sql);
        return list;
    }

    @Override
    public List<Hostel> queryHostelByCity(String city) {
        String sql="select * from hostelworld.hostel as h where h.city='"+city+"' and h.approvalState='"+ApprovalStateEnum.APPROVE.toString()+"';";
        List<Object[]> objects=super.querySQL(sql);
        List<Hostel> hostels=this.getHostels(objects);
        return hostels;
    }

    @Override
    public List<Hostel> queryHostelByName(String hostelName) {
        String sql="select * from hostelworld.hostel as h where h.hostelName='"+hostelName+"';";
        List<Object[]> objects=super.querySQL(sql);
        List<Hostel> hostels=this.getHostels(objects);
        return hostels;
    }

    @Override
    public Hostel queryHostelByNum(String hostelNum) {
        Hostel hostel=(Hostel) super.load(Hostel.class,hostelNum);
        return hostel;
    }

    @Override
    public List<Hostel> queryHostelByLevel(int level) {
        String sql="select * from hostelworld.hostel as h where h.level="+level+";";
        List<Hostel> list=super.querySQL(sql);
        return list;
    }

    @Override
    public List<Hostel> queryAll() {
        return super.getAllList(Hostel.class);
    }

    @Override
    public List<Hostel> queryByApprove(String approveState) {
        String sql="select * from hostelworld.hostel as h where h.approvalState='"+approveState+"';";
        List<Object[]> objects=super.querySQL(sql);
        List<Hostel> hostels=this.getHostels(objects);
        return hostels;
    }

    private List<Hostel> getHostels(List<Object[]> objects){
        List<Hostel> hostels=new ArrayList<Hostel>();
        for(Object[] object:objects){
            Hostel hostel=new Hostel();
            hostel.setHostelNum(String.valueOf(object[0]));
            hostel.setHostelPassword(String.valueOf(object[7]));
            hostel.setProfit((double)object[9]);
            hostel.setProvince(String.valueOf(object[10]));
            hostel.setCity(String.valueOf(object[4]));
            hostel.setAddress(String.valueOf(object[1]));
            hostel.setHostelInfo(String.valueOf(object[5]));
            hostel.setApprovalState(String.valueOf(object[3]));
            hostel.setLevel((int)object[8]);
            hostel.setHostelName(String.valueOf(object[6]));;
            hostel.setApplyDate(String.valueOf(object[2]));
            hostels.add(hostel);
        }
        return hostels;
    }


}
