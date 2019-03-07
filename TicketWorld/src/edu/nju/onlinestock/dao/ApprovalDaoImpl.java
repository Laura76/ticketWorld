package edu.nju.onlinestock.dao;

import edu.nju.onlinestock.dao.ApprovalDao;
import edu.nju.onlinestock.dao.BaseDao;
import edu.nju.onlinestock.model.Approval;

import java.util.ArrayList;
import java.util.List;

public class ApprovalDaoImpl extends BaseDaoImpl implements ApprovalDao{

    private static ApprovalDaoImpl approvalDao = new ApprovalDaoImpl();

	private ApprovalDaoImpl() {

	}

	public static ApprovalDaoImpl getInstance() {
		return approvalDao;
	}

    @Override
    public Approval find(int approvalNum) {
        return (Approval) super.load(Approval.class,approvalNum);
    }

    @Override
    public void save(Approval approval) {
        super.save(approval);
    }

    @Override
    public void update(Approval approval) {
        super.update(approval);
    }

    @Override
    public List<Approval> queryByState(String state) {
        String sql="select * from hostelworld.approval as a where a.approveState='"+state+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getApprovals(objects);
    }


    @Override
    public List<Approval> queryByType(String type) {
        String sql="select * from hostelworld.approval as a where a.approvalType='"+type+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getApprovals(objects);
    }

    @Override
    public List<Approval> queryByHostel(String hostelNum) {
        String sql="select * from hostelworld.approval as a where a.hostelNum='"+hostelNum+"';";
        List<Object[]> objects=super.querySQL(sql);
        return this.getApprovals(objects);
    }

    @Override
    public List<Approval> getAllList() {
        return super.getAllList(Approval.class);
    }

    private List<Approval> getApprovals(List<Object[]> objects){
        List<Approval> approvals=new ArrayList<>();
        for(Object[] object:objects){
            Approval approval=new Approval();
            approval.setApprovalNum((int)object[0]);
            approval.setHostelNum(String.valueOf(object[5]));
            approval.setApproveDate(String.valueOf(object[3]));
            approval.setApprovalType(String.valueOf(object[2]));
            approval.setApproveState(String.valueOf(object[4]));
            approval.setApplyDate(String.valueOf(object[1]));
            approvals.add(approval);
        }
        return approvals;
    }
}
